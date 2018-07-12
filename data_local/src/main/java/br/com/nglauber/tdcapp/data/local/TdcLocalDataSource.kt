package br.com.nglauber.tdcapp.data.local

import android.content.Context
import androidx.room.Room
import br.com.nglauber.tdcapp.data.LocalDataSource
import br.com.nglauber.tdcapp.data.local.db.TdcDatabase
import br.com.nglauber.tdcapp.data.local.mapper.SessionMapper
import br.com.nglauber.tdcapp.data.local.mapper.SpeakerMapper
import br.com.nglauber.tdcapp.domain.model.Session
import br.com.nglauber.tdcapp.domain.model.Speaker
import io.reactivex.Completable
import io.reactivex.Observable

class TdcLocalDataSource(context: Context, allowMainThreadQueries: Boolean = false) : LocalDataSource {
    private val database: TdcDatabase by lazy {
        val builder = Room.databaseBuilder(
                context.applicationContext, TdcDatabase::class.java, "tdcDb")
        if (allowMainThreadQueries) {
            builder.allowMainThreadQueries()
        }
        builder.build()
    }
    private val sessionDao = database.sessionDao()
    private val speakerDao = database.speakerDao()
    private val sessionSpeakerJoinDao = database.sessionSpeakerJoinDao()

    override fun getBookmarkedSessions(eventId: Long, modalityId: Long): Observable<List<Session>> {
        return sessionDao.sessionsByModality(eventId, modalityId)
                .map {
                    it.map {
                        SessionMapper.toDomain(it)
                    }
                }
                .toObservable()
    }

    override fun bookmarkSession(session: Session, speakers: List<Speaker>): Completable {
        return Completable.defer {
            sessionSpeakerJoinDao.insertSessionWithSpeakers(
                    SessionMapper.fromDomain(session),
                    speakers.map {
                        SpeakerMapper.fromDomain(it)
                    },
                    sessionDao,
                    speakerDao
            )
            Completable.complete()
        }
    }

    override fun unbookmarkSession(session: Session): Completable {
        return Completable.defer {
            sessionSpeakerJoinDao.removeSession(SessionMapper.fromDomain(session), sessionDao)
            Completable.complete()
        }
    }
}