package br.com.nglauber.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.nglauber.data.local.test.DomainDataFactory
import br.com.nglauber.tdcapp.data.local.TdcLocalDataSource
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class TdcLocalDataSourceTest {
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var dataSource: TdcLocalDataSource

    @Before
    @Throws(Exception::class)
    fun initDb() {
        dataSource = TdcLocalDataSource(RuntimeEnvironment.application.applicationContext, true)
    }

    @Test
    fun bookmarkSessionCompletes() {
        val session = DomainDataFactory.makeSession(false)
        val speakers = DomainDataFactory.makeSpeakersList(2)
        dataSource.bookmarkSession(session, speakers)
                .test()
                .assertComplete()
    }

    @Test
    fun bookmarkSessionWasInserted() {
        val session = DomainDataFactory.makeSession(false)
        val speakers = DomainDataFactory.makeSpeakersList(2)
        dataSource.bookmarkSession(session, speakers)
                .test()
                .assertComplete()
        dataSource.getBookmarkedSessions(session.eventId, session.modalityId)
                .test()
                .assertValues(listOf(session.copy(bookmarked = true)))
    }

    @Test
    fun unbookmarkSessionCompletes() {
        val session = DomainDataFactory.makeSession(false)
        val speakers = DomainDataFactory.makeSpeakersList(2)
        // Insert
        dataSource.bookmarkSession(session, speakers)
                .test()
                .assertComplete()
        // Check if it was inserted
        dataSource.getBookmarkedSessions(session.eventId, session.modalityId)
                .test()
                .assertValues(listOf(session.copy(bookmarked = true)))
        // Remove
        dataSource.unbookmarkSession(session)
                .test()
                .assertComplete()
        // Check it was removed
        dataSource.getBookmarkedSessions(session.eventId, session.modalityId)
                .test()
                .assertValues(emptyList())
    }
}