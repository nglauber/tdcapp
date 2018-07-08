package br.com.nglauber.tdcapp.domain.repository

import br.com.nglauber.tdcapp.domain.model.Event
import br.com.nglauber.tdcapp.domain.model.Modality
import br.com.nglauber.tdcapp.domain.model.Session
import br.com.nglauber.tdcapp.domain.model.Speaker
import io.reactivex.Observable

interface TdcRepository {
    fun getEvents(): Observable<List<Event>>

    fun getModalitiesByEvent(eventId: Int): Observable<List<Modality>>

    fun getSessionsByModality(eventId: Int, modalityId: Int): Observable<List<Session>>

    fun getSpeakersBySession(eventId: Int, modalityId: Int, sessionId: Int): Observable<List<Speaker>>
}