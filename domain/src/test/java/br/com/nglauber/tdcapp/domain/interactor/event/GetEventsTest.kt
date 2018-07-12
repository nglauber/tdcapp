package br.com.nglauber.tdcapp.domain.interactor.event

import br.com.nglauber.tdcapp.domain.executor.PostExecutionThread
import br.com.nglauber.tdcapp.domain.model.Event
import br.com.nglauber.tdcapp.domain.repository.Repository
import br.com.nglauber.tdcapp.domain.test.DomainDataFactory
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class GetEventsTest {
    private lateinit var getEvents: GetEvents
    @Mock
    lateinit var repository: Repository
    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getEvents = GetEvents(repository, postExecutionThread)
    }

    @Test
    fun getEventsCompletes() {
        val list = DomainDataFactory.makeEventsList(3)
        stubGetEvents(Observable.just(list))
        val testObserver = getEvents.buildUseCaseObservable().test()
        testObserver.assertComplete()
    }

    @Test
    fun getEventsReturnsData() {
        val list = DomainDataFactory.makeEventsList(3)
        stubGetEvents(Observable.just(list))
        val testObserver = getEvents.buildUseCaseObservable().test()
        testObserver.assertValue { result ->
            result.sortedBy { it.id } == list.sortedBy { it.id }
        }
    }

    private fun stubGetEvents(observable: Observable<List<Event>>) {
        whenever(repository.getEvents()).thenReturn(observable)
    }
}