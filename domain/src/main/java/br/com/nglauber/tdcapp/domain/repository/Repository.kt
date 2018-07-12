package br.com.nglauber.tdcapp.domain.repository

import br.com.nglauber.tdcapp.domain.model.Event
import br.com.nglauber.tdcapp.domain.model.Modality
import br.com.nglauber.tdcapp.domain.model.Session
import br.com.nglauber.tdcapp.domain.model.Speaker
import io.reactivex.Completable
import io.reactivex.Observable

interface Repository {
    fun getEvents(): Observable<List<Event>>

    fun getModalitiesByEvent(eventId: Long): Observable<List<Modality>>

    fun getSessionsByModality(eventId: Long, modalityId: Long): Observable<List<Session>>

    fun getSpeakersBySession(eventId: Long, modalityId: Long, sessionId: Long): Observable<List<Speaker>>

    fun bookmarkSession(session: Session, speakers: List<Speaker>): Completable

    fun unbookmarkSession(session: Session): Completable
}