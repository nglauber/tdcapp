package br.com.nglauber.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import br.com.nglauber.data.local.test.EntityDataFactory
import br.com.nglauber.tdcapp.data.local.dao.SpeakerDao
import br.com.nglauber.tdcapp.data.local.db.TdcDatabase
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class SpeakerDaoTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var db: TdcDatabase
    lateinit var speakerDao: SpeakerDao

    @Before
    @Throws(Exception::class)
    fun initDb() {
        db = Room.inMemoryDatabaseBuilder(
                RuntimeEnvironment.application.applicationContext,
                TdcDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        speakerDao = db.speakerDao()
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertSpeakerSuccess() {
        val speaker = EntityDataFactory.makeSpeaker()
        val id = speakerDao.insert(speaker)
        Assert.assertNotEquals(id, -1)
        Assert.assertEquals(id, speaker.id)
    }
}