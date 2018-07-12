package br.com.nglauber.tdcapp.domain.interactor.session

import br.com.nglauber.tdcapp.domain.executor.PostExecutionThread
import br.com.nglauber.tdcapp.domain.model.Session
import br.com.nglauber.tdcapp.domain.repository.Repository
import br.com.nglauber.tdcapp.domain.test.DomainDataFactory
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class GetSessionsByModalityTest {
    private lateinit var getSessionsByModality: GetSessionsByModality
    @Mock
    lateinit var repository: Repository
    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getSessionsByModality = GetSessionsByModality(repository, postExecutionThread)
    }

    @Test
    fun getSessionsByModalityCompletes() {
        val list = DomainDataFactory.makeSessionsList(3)
        stubGetSessions(Observable.just(list))
        val testObserver = getSessionsByModality.buildUseCaseObservable(
                GetSessionsByModality.Params(1, 1)
        ).test()
        testObserver.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun getSessionsWithNoParamsMustThrowException() {
        val list = DomainDataFactory.makeSessionsList(3)
        stubGetSessions(Observable.just(list))
        getSessionsByModality.buildUseCaseObservable().test()
    }

    @Test
    fun getSessionsReturnsData() {
        val list = DomainDataFactory.makeSessionsList(3)
        stubGetSessions(Observable.just(list))
        val testObserver = getSessionsByModality.buildUseCaseObservable(
                GetSessionsByModality.Params(1, 1)
        ).test()
        testObserver.assertValue { result ->
            result.sortedBy { it.id } == list.sortedBy { it.id }
        }
    }

    private fun stubGetSessions(observable: Observable<List<Session>>) {
        whenever(repository.getSessionsByModality(any(), any())).thenReturn(observable)
    }
}