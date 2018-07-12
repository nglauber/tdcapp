package br.com.nglauber.tdcapp.data.local.dao

import androidx.room.*
import br.com.nglauber.tdcapp.data.local.entities.Session
import br.com.nglauber.tdcapp.data.local.entities.SessionSpeakerJoin
import br.com.nglauber.tdcapp.data.local.entities.Speaker

@Dao
interface SessionSpeakerJoinDao {

    @Insert
    fun insert(sessionSpeakerJoin: SessionSpeakerJoin)

    @Query("DELETE FROM SessionSpeakerJoin WHERE sessionId = :sessionId")
    fun deleteBySessionId(sessionId: Long): Int

    @Query("DELETE FROM Speaker WHERE id NOT IN (SELECT speakerId FROM SessionSpeakerJoin)")
    fun deleteSpeakersWithNoSessions(): Int

    @Transaction
    fun insertSessionWithSpeakers(session: Session,
                                  speakers: List<Speaker>,
                                  sessionDao: SessionDao,
                                  speakerDao: SpeakerDao) {
        sessionDao.insert(session)
        speakers.forEach { speaker ->
            speakerDao.insert(speaker)
            insert(SessionSpeakerJoin(session.id, speaker.id))
        }
    }

    @Transaction
    fun removeSession(session: Session, sessionDao: SessionDao) {
        sessionDao.deleteById(session.id)
        deleteSpeakersWithNoSessions()
    }
}