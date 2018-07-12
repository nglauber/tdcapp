package br.com.nglauber.tdcapp.data.remote

import br.com.nglauber.tdcapp.data.RemoteDataSource

import br.com.nglauber.tdcapp.data.remote.mapper.EventMapper
import br.com.nglauber.tdcapp.data.remote.mapper.ModalityMapper
import br.com.nglauber.tdcapp.data.remote.mapper.SessionMapper
import br.com.nglauber.tdcapp.data.remote.mapper.SpeakerMapper
import br.com.nglauber.tdcapp.data.remote.service.TdcWebService
import br.com.nglauber.tdcapp.domain.model.Event
import br.com.nglauber.tdcapp.domain.model.Modality
import br.com.nglauber.tdcapp.domain.model.Session
import br.com.nglauber.tdcapp.domain.model.Speaker
import io.reactivex.Observable

class TdcRemoteDataSource (
        private val tdcWebService: TdcWebService
) : RemoteDataSource {
    override fun getEvents(): Observable<List<Event>> {
        return tdcWebService.getEvents()
                .map { events ->
                    events.map { EventMapper.parse(it) }
                }
    }

    override fun getModalitiesByEvent(eventId: Long): Observable<List<Modality>> {
        return tdcWebService.getModalitiesByEvent(eventId)
                .map { modalities ->
                    modalities.map { ModalityMapper.parse(it) }
                }
    }

    override fun getSessionsByModality(eventId: Long, modalityId: Long): Observable<List<Session>> {
        return tdcWebService.getSessionsByModality(eventId, modalityId)
                .map { sessions ->
                    sessions.map {
                        SessionMapper.parse(it).copy(
                            eventId = eventId,
                            modalityId = modalityId
                        )
                    }
                }
    }

    override fun getSpeakersBySession(eventId: Long, modalityId: Long, sessionId: Long): Observable<List<Speaker>> {
        return tdcWebService.getSpeakersBySession(eventId, modalityId, sessionId)
                .map { speakers ->
                    speakers.map { SpeakerMapper.parse(it) }
                }
    }
}