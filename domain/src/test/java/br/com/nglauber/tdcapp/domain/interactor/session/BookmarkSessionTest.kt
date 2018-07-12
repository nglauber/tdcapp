package br.com.nglauber.tdcapp.domain.interactor.session

import br.com.nglauber.tdcapp.domain.executor.PostExecutionThread
import br.com.nglauber.tdcapp.domain.repository.Repository
import br.com.nglauber.tdcapp.domain.test.DomainDataFactory
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class BookmarkSessionTest {
    private lateinit var bookmarkSession: BookmarkSession
    @Mock
    lateinit var repository: Repository
    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        bookmarkSession = BookmarkSession(repository, postExecutionThread)
    }

    @Test
    fun bookmarkSessionCompletes() {
        val session = DomainDataFactory.makeSession()
        val speakersList = DomainDataFactory.makeSpeakersList(2)
        stubBookmarkSession(Completable.complete())
        val testObserver = bookmarkSession.buildUseCaseCompletable(
                BookmarkSession.Params(session, speakersList)
        ).test()
        testObserver.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun bookmarkProjectThrowsException() {
        bookmarkSession.buildUseCaseCompletable().test()
    }

    private fun stubBookmarkSession(completable: Completable) {
        whenever(repository.bookmarkSession(any(), any()))
                .thenReturn(completable)
    }
}