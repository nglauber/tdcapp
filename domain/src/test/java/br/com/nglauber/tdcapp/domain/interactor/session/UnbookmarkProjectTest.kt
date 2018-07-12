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
class UnbookmarkProjectTest {
    private lateinit var unbookmarkSession: UnbookmarkSession
    @Mock
    lateinit var repository: Repository
    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        unbookmarkSession = UnbookmarkSession(repository, postExecutionThread)
    }

    @Test
    fun unbookmarkSessionCompletes() {
        val session = DomainDataFactory.makeSession()
        stubUnbookmarkProject(Completable.complete())
        val testObserver = unbookmarkSession.buildUseCaseCompletable(
                UnbookmarkSession.Params(session)
        ).test()
        testObserver.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun unbookmarkProjectThrowsException() {
        unbookmarkSession.buildUseCaseCompletable().test()
    }

    private fun stubUnbookmarkProject(completable: Completable) {
        whenever(repository.unbookmarkSession(any()))
                .thenReturn(completable)
    }
}