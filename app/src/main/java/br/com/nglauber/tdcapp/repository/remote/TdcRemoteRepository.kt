package br.com.nglauber.tdcapp.repository.remote

import br.com.nglauber.tdcapp.repository.TdcRepository
import br.com.nglauber.tdcapp.repository.model.Event
import br.com.nglauber.tdcapp.repository.model.Modality
import br.com.nglauber.tdcapp.repository.model.Session
import br.com.nglauber.tdcapp.repository.model.Speaker
import br.com.nglauber.tdcapp.repository.remote.mapper.EventMapper
import br.com.nglauber.tdcapp.repository.remote.mapper.ModalityMapper
import br.com.nglauber.tdcapp.repository.remote.mapper.SessionMapper
import br.com.nglauber.tdcapp.repository.remote.mapper.SpeakerMapper
import br.com.nglauber.tdcapp.repository.remote.service.TdcWebService
import io.reactivex.Observable

class TdcRemoteRepository(private val tdcWebService: TdcWebService): TdcRepository {
    override fun getEvents(): Observable<List<Event>> {
        return tdcWebService.getEvents()
                .map { events ->
                    events.map { EventMapper.parse(it) }
                }
    }

    override fun getModalitiesByEvent(eventId: Int): Observable<List<Modality>> {
        return tdcWebService.getModalitiesByEvent(eventId)
                .map { modalities ->
                    modalities.map { ModalityMapper.parse(it) }
                }
    }

    override fun getSessionsByModality(eventId: Int, modalityId: Int): Observable<List<Session>> {
        return tdcWebService.getSessionsByModality(eventId, modalityId)
                .map { sessions ->
                    sessions.map { SessionMapper.parse(it) }
                }
    }

    override fun getSpeakersBySession(eventId: Int, modalityId: Int, sessionId: Int): Observable<List<Speaker>> {
        return tdcWebService.getSpeakersBySession(eventId, modalityId, sessionId)
                .map { speakers ->
                    speakers.map { SpeakerMapper.parse(it) }
                }
    }
}