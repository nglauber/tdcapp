package br.com.nglauber.tdcapp.data.remote

import br.com.nglauber.tdcapp.data.remote.mapper.EventMapper
import br.com.nglauber.tdcapp.data.remote.mapper.ModalityMapper
import br.com.nglauber.tdcapp.data.remote.mapper.SessionMapper
import br.com.nglauber.tdcapp.data.remote.mapper.SpeakerMapper
import br.com.nglauber.tdcapp.data.remote.model.TdcEvent
import br.com.nglauber.tdcapp.data.remote.model.TdcModality
import br.com.nglauber.tdcapp.data.remote.model.TdcSession
import br.com.nglauber.tdcapp.data.remote.model.TdcSpeaker
import br.com.nglauber.tdcapp.data.remote.service.TdcWebService
import br.com.nglauber.tdcapp.domain.model.Event
import br.com.nglauber.tdcapp.domain.model.Modality
import br.com.nglauber.tdcapp.domain.model.Session
import br.com.nglauber.tdcapp.domain.model.Speaker
import br.com.nglauber.tdcapp.data.remote.test.RemoteDataFactory
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TdcRemoteDataSourceTest {
    private val service = mock<TdcWebService>()
    private val remote = TdcRemoteDataSource(service)

    // Events
    @Test
    fun getEventsCompletes() {
        stubTdcWebServiceGetEvents(
                Observable.just(RemoteDataFactory.makeEventsList(2)))
        val testObserver = remote.getEvents().test()
        testObserver.assertComplete()
    }

    @Test
    fun getEventsCallServer() {
        stubTdcWebServiceGetEvents(
                Observable.just(RemoteDataFactory.makeEventsList(2))
        )
        remote.getEvents().test()
        verify(service).getEvents()
    }

    @Test
    fun getEventsReturnsData() {
        val remoteEventList = RemoteDataFactory.makeEventsList(3)
        stubTdcWebServiceGetEvents(
                Observable.just(remoteEventList)
        )
        val domainEventList = mutableListOf<Event>()
        remoteEventList.forEach { tdcEvent ->
            val entity = EventMapper.parse(tdcEvent)
            domainEventList.add(entity)
        }
        val testObserver = remote.getEvents().test()
        testObserver.assertValue { result ->
            result.sortedBy { it.id } == domainEventList.sortedBy { it.id }
        }
    }

    // Modalities
    @Test
    fun getModalitiesCompletes() {
        stubTdcWebServiceGetModalities(
                Observable.just(RemoteDataFactory.makeModalitiesList(2))
        )
        val testObserver = remote.getModalitiesByEvent(any()).test()
        testObserver.assertComplete()
    }

    @Test
    fun getModalitiesCallServer() {
        stubTdcWebServiceGetModalities(
                Observable.just(RemoteDataFactory.makeModalitiesList(2))
        )
        remote.getModalitiesByEvent(any()).test()
        verify(service).getModalitiesByEvent(any())
    }

    @Test
    fun getModalitiesCallServerWithCorrectParams() {
        stubTdcWebServiceGetModalities(
                Observable.just(RemoteDataFactory.makeModalitiesList(2))
        )
        remote.getModalitiesByEvent(12).test()
        verify(service).getModalitiesByEvent(12)
    }

    @Test
    fun getModalitiesReturnsData() {
        val remoteModalityList = RemoteDataFactory.makeModalitiesList(3)
        stubTdcWebServiceGetModalities(
                Observable.just(remoteModalityList)
        )
        val domainModalityList = mutableListOf<Modality>()
        remoteModalityList.forEach { tdcModality ->
            val modality = ModalityMapper.parse(tdcModality)
            domainModalityList.add(modality)
        }
        val testObserver = remote.getModalitiesByEvent(any()).test()
        testObserver.assertValue { result ->
            result.sortedBy { it.id } == domainModalityList.sortedBy { it.id }
        }
    }

    // Sessions
    @Test
    fun getSessionsCompletes() {
        stubTdcWebServiceGetSessions(
                Observable.just(RemoteDataFactory.makeSessionsList(2))
        )
        val testObserver = remote.getSessionsByModality(any(), any()).test()
        testObserver.assertComplete()
    }

    @Test
    fun getSessionsCallServer() {
        stubTdcWebServiceGetSessions(
                Observable.just(RemoteDataFactory.makeSessionsList(2))
        )
        remote.getSessionsByModality(any(), any()).test()
        verify(service).getSessionsByModality(any(), any())
    }

    @Test
    fun getSessionsCallServerWithCorrectParams() {
        stubTdcWebServiceGetSessions(
                Observable.just(RemoteDataFactory.makeSessionsList(2))
        )
        remote.getSessionsByModality(12, 84).test()
        verify(service).getSessionsByModality(12, 84)
    }

    @Test
    fun getSessionsReturnsData() {
        val remoteSessionList = RemoteDataFactory.makeSessionsList(3)
        stubTdcWebServiceGetSessions(
                Observable.just(remoteSessionList)
        )
        val domainSessionList = mutableListOf<Session>()
        remoteSessionList.forEach { tdcSession ->
            val session = SessionMapper.parse(tdcSession)
            domainSessionList.add(session)
        }
        val testObserver = remote.getSessionsByModality(any(), any()).test()
        testObserver.assertValue { result ->
            result.sortedBy { it.id } == domainSessionList.sortedBy { it.id }
        }
    }

    // Speakers
    @Test
    fun getSpeakersCompletes() {
        stubTdcWebServiceGetSpeakers(
                Observable.just(RemoteDataFactory.makeSpeakersList(2))
        )
        val testObserver = remote.getSpeakersBySession(any(), any(), any()).test()
        testObserver.assertComplete()
    }

    @Test
    fun getSpeakersCallServer() {
        stubTdcWebServiceGetSpeakers(
                Observable.just(RemoteDataFactory.makeSpeakersList(2))
        )
        remote.getSpeakersBySession(any(), any(), any()).test()
        verify(service).getSpeakersBySession(any(), any(), any())
    }

    @Test
    fun getSpeakersCallServerWithCorrectParams() {
        stubTdcWebServiceGetSpeakers(
                Observable.just(RemoteDataFactory.makeSpeakersList(2))
        )
        remote.getSpeakersBySession(1, 2, 3).test()
        verify(service).getSpeakersBySession(1, 2, 3)
    }

    @Test
    fun getSpeakersReturnsData() {
        val remoteSpeakersList = RemoteDataFactory.makeSpeakersList(3)
        stubTdcWebServiceGetSpeakers(
                Observable.just(remoteSpeakersList)
        )
        val domainSpeakerList = mutableListOf<Speaker>()
        remoteSpeakersList.forEach { tdcSpeaker ->
            val speaker = SpeakerMapper.parse(tdcSpeaker)
            domainSpeakerList.add(speaker)
        }
        val testObserver = remote.getSpeakersBySession(any(), any(), any()).test()
        testObserver.assertValue { result ->
            result.sortedBy { it.id } == domainSpeakerList.sortedBy { it.id }
        }
    }

    // Helper
    private fun stubTdcWebServiceGetEvents(observable: Observable<List<TdcEvent>>) {
        whenever(service.getEvents()).thenReturn(observable)
    }

    private fun stubTdcWebServiceGetModalities(observable: Observable<List<TdcModality>>) {
        whenever(service.getModalitiesByEvent(any())).thenReturn(observable)
    }

    private fun stubTdcWebServiceGetSessions(observable: Observable<List<TdcSession>>) {
        whenever(service.getSessionsByModality(any(), any())).thenReturn(observable)
    }

    private fun stubTdcWebServiceGetSpeakers(observable: Observable<List<TdcSpeaker>>) {
        whenever(service.getSpeakersBySession(any(), any(), any())).thenReturn(observable)
    }
}