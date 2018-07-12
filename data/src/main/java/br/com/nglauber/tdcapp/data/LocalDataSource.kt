package br.com.nglauber.tdcapp.data

import br.com.nglauber.tdcapp.domain.model.Session
import br.com.nglauber.tdcapp.domain.model.Speaker
import io.reactivex.Completable
import io.reactivex.Observable

interface LocalDataSource {
    fun getBookmarkedSessions(eventId: Long, modalityId: Long): Observable<List<Session>>

    fun bookmarkSession(session: Session, speakers: List<Speaker>): Completable

    fun unbookmarkSession(session: Session): Completable
}