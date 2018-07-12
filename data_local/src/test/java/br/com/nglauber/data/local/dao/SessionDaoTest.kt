package br.com.nglauber.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import br.com.nglauber.data.local.test.DataFactory
import br.com.nglauber.data.local.test.EntityDataFactory
import br.com.nglauber.tdcapp.data.local.dao.SessionDao
import br.com.nglauber.tdcapp.data.local.db.TdcDatabase
import org.junit.*
import org.junit.Assert.assertTrue
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class SessionDaoTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var db: TdcDatabase
    lateinit var sessionDao: SessionDao

    @Before
    @Throws(Exception::class)
    fun initDb() {
        db = Room.inMemoryDatabaseBuilder(
                RuntimeEnvironment.application.applicationContext,
                TdcDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        sessionDao = db.sessionDao()
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertSessionSuccess() {
        val session = EntityDataFactory.makeSession()
        val id = sessionDao.insert(session)
        Assert.assertNotEquals(id, -1)
        Assert.assertEquals(id, session.id)
    }

    @Test
    fun sessionsByModalityReturnsData() {
        val sessions = EntityDataFactory.makeSessionsList(3).sortedBy { it.id }
        sessions.forEach {
            val id = sessionDao.insert(it)
            Assert.assertNotEquals(id, -1)
            Assert.assertEquals(id, it.id.toLong())
        }
        val first = sessions.first()
        val eventId = first.eventId
        val modalityId = first.modalityId
        val testObserver = sessionDao.sessionsByModality(eventId, modalityId).test()
        testObserver.assertValues(listOf(first))
    }

    @Test
    fun sessionByModalityReturnEmptyList() {
        val testObserver = sessionDao.sessionsByModality(
                DataFactory.randomLong(),
                DataFactory.randomLong()
        ).test()
        testObserver.assertValues(emptyList())
    }

    @Test
    fun deleteByIdTest() {
        val session = EntityDataFactory.makeSession()
        val id = sessionDao.insert(session)
        Assert.assertNotEquals(id, -1)
        Assert.assertEquals(id, session.id)

        assertTrue(sessionDao.deleteById(session.id) > 0)
        sessionDao.sessionsByModality(session.eventId, session.modalityId)
                .test()
                .assertValues(emptyList())
    }
}