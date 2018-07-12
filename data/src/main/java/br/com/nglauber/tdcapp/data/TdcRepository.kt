package br.com.nglauber.tdcapp.data

import br.com.nglauber.tdcapp.domain.model.Event
import br.com.nglauber.tdcapp.domain.model.Modality
import br.com.nglauber.tdcapp.domain.model.Session
import br.com.nglauber.tdcapp.domain.model.Speaker
import br.com.nglauber.tdcapp.domain.repository.Repository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class TdcRepository(
        private val remoteDataSource: RemoteDataSource,
        private val localDataSource: LocalDataSource

) : Repository {
    override fun getEvents(): Observable<List<Event>> {
        return remoteDataSource.getEvents()
    }

    override fun getModalitiesByEvent(eventId: Long): Observable<List<Modality>> {
        return remoteDataSource.getModalitiesByEvent(eventId)
    }

    override fun getSessionsByModality(eventId: Long, modalityId: Long): Observable<List<Session>> {
        return Observable.zip(
                remoteDataSource.getSessionsByModality(eventId, modalityId),
                localDataSource.getBookmarkedSessions(eventId, modalityId),
                BiFunction { remoteList, localList ->
                    remoteList.map { remoteSession ->
                        if (localList.find { it.id == remoteSession.id } != null) {
                            remoteSession.copy(bookmarked = true)
                        } else {
                            remoteSession
                        }
                    }
                }
        )
    }

    override fun getSpeakersBySession(eventId: Long, modalityId: Long, sessionId: Long): Observable<List<Speaker>> {
        return remoteDataSource.getSpeakersBySession(eventId, modalityId, sessionId)
    }

    override fun bookmarkSession(session: Session, speakers: List<Speaker>): Completable {
        return localDataSource.bookmarkSession(session, speakers)
    }

    override fun unbookmarkSession(session: Session): Completable {
        return localDataSource.unbookmarkSession(session)
    }
}