package br.com.nglauber.data.local.dao

import androidx.room.Room
import br.com.nglauber.tdcapp.data.local.db.TdcDatabase
import br.com.nglauber.data.local.test.EntityDataFactory
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.nglauber.tdcapp.data.local.dao.SessionDao
import br.com.nglauber.tdcapp.data.local.dao.SessionSpeakerJoinDao
import br.com.nglauber.tdcapp.data.local.dao.SpeakerDao
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class SessionSpeakerJoinTest {

    @Rule
    @JvmField var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var db: TdcDatabase
    lateinit var sessionDao: SessionDao
    lateinit var speakerDao: SpeakerDao
    lateinit var sessionWithSpeakerJoinDao: SessionSpeakerJoinDao

    @Before
    @Throws(Exception::class)
    fun initDb() {
        db = Room.inMemoryDatabaseBuilder(
                RuntimeEnvironment.application.applicationContext,
                TdcDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        sessionDao = db.sessionDao()
        speakerDao = db.speakerDao()
        sessionWithSpeakerJoinDao = db.sessionSpeakerJoinDao()
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertSessionWithSpeakers() {
        val session = EntityDataFactory.makeSession()
        val speakers = EntityDataFactory.makeSpeakersList(2).sortedBy { it.id }
        sessionWithSpeakerJoinDao.insertSessionWithSpeakers(session, speakers, sessionDao, speakerDao)

        sessionDao.sessionsByModality(session.eventId, session.modalityId)
                .toObservable()
                .test()
                .assertValues(listOf(session))
        speakerDao.speakerById(speakers[0].id)
                .toObservable()
                .test()
                .assertValue(speakers[0])
        speakerDao.speakerById(speakers[1].id)
                .toObservable()
                .test()
                .assertValue(speakers[1])
    }

    @Test
    fun removeSessionWithSpeakers() {
        val session = EntityDataFactory.makeSession()
        val speakers = EntityDataFactory.makeSpeakersList(2).sortedBy { it.id }
        sessionWithSpeakerJoinDao.insertSessionWithSpeakers(session, speakers, sessionDao, speakerDao)

        sessionWithSpeakerJoinDao.removeSession(session, sessionDao)
        val testObserver = sessionDao.sessionsByModality(session.eventId, session.modalityId).toObservable().test()
        testObserver.assertValues(emptyList())

        speakerDao.speakerById(speakers[0].id)
                .toObservable()
                .test()
                .assertNoValues()
        speakerDao.speakerById(speakers[1].id)
                .toObservable()
                .test()
                .assertNoValues()
    }
}