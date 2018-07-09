package br.com.nglauber.tdcapp.domain.interactor.modality

import br.com.nglauber.tdcapp.domain.executor.PostExecutionThread
import br.com.nglauber.tdcapp.domain.model.Modality
import br.com.nglauber.tdcapp.domain.repository.TdcRepository
import br.com.nglauber.tdcapp.test.DomainDataFactory
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
class GetModalitiesTest {
    private lateinit var getModalitiesByEvent: GetModalitiesByEvent
    @Mock
    lateinit var repository: TdcRepository
    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getModalitiesByEvent = GetModalitiesByEvent(repository, postExecutionThread)
    }

    @Test
    fun getModalityCompletes() {
        val list = DomainDataFactory.makeModalitiesList(3)
        stubGetModalities(Observable.just(list))
        val testObserver = getModalitiesByEvent.buildUseCaseObservable(1).test()
        testObserver.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun getEventsWithNoParamsMustThrowException() {
        val list = DomainDataFactory.makeModalitiesList(3)
        stubGetModalities(Observable.just(list))
        getModalitiesByEvent.buildUseCaseObservable().test()
    }

    @Test
    fun getEventsReturnsData() {
        val list = DomainDataFactory.makeModalitiesList(3)
        stubGetModalities(Observable.just(list))
        val testObserver = getModalitiesByEvent.buildUseCaseObservable(1).test()
        testObserver.assertValue { result ->
            result.sortedBy { it.id } == list.sortedBy { it.id }
        }
    }

    private fun stubGetModalities(observable: Observable<List<Modality>>) {
        whenever(repository.getModalitiesByEvent(any())).thenReturn(observable)
    }
}