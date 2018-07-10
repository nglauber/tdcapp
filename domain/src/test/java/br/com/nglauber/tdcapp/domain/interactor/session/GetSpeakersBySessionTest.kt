package br.com.nglauber.tdcapp.domain.interactor.session

import br.com.nglauber.tdcapp.domain.executor.PostExecutionThread
import br.com.nglauber.tdcapp.domain.model.Speaker
import br.com.nglauber.tdcapp.domain.repository.TdcRepository
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
class GetSpeakersBySessionTest {
    private lateinit var getSpeakersBySession: GetSpeakersBySession
    @Mock
    lateinit var repository: TdcRepository
    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getSpeakersBySession = GetSpeakersBySession(repository, postExecutionThread)
    }

    @Test
    fun getSpeakersByModalityCompletes() {
        val list = DomainDataFactory.makeSpeakersList(3)
        stubGetSpeakers(Observable.just(list))
        val testObserver = getSpeakersBySession.buildUseCaseObservable(
                GetSpeakersBySession.Params(1, 2, 3)
        ).test()
        testObserver.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun getSpeakersWithNoParamsMustThrowException() {
        val list = DomainDataFactory.makeSpeakersList(3)
        stubGetSpeakers(Observable.just(list))
        getSpeakersBySession.buildUseCaseObservable().test()
    }

    @Test
    fun getSpeakersReturnsData() {
        val list = DomainDataFactory.makeSpeakersList(3)
        stubGetSpeakers(Observable.just(list))
        val testObserver = getSpeakersBySession.buildUseCaseObservable(
                GetSpeakersBySession.Params(1, 2, 3)
        ).test()
        testObserver.assertValue { result ->
            result.sortedBy { it.id } == list.sortedBy { it.id }
        }
    }

    private fun stubGetSpeakers(observable: Observable<List<Speaker>>) {
        whenever(repository.getSpeakersBySession(any(), any(), any())).thenReturn(observable)
    }
}