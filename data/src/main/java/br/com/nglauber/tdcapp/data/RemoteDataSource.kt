package br.com.nglauber.tdcapp.data

import br.com.nglauber.tdcapp.domain.model.Event
import br.com.nglauber.tdcapp.domain.model.Modality
import br.com.nglauber.tdcapp.domain.model.Session
import br.com.nglauber.tdcapp.domain.model.Speaker
import io.reactivex.Observable

interface RemoteDataSource {
    fun getEvents(): Observable<List<Event>>

    fun getModalitiesByEvent(eventId: Long): Observable<List<Modality>>

    fun getSessionsByModality(eventId: Long, modalityId: Long): Observable<List<Session>>

    fun getSpeakersBySession(eventId: Long, modalityId: Long, sessionId: Long): Observable<List<Speaker>>
}