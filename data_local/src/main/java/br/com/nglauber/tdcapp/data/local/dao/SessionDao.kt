package br.com.nglauber.tdcapp.data.local.dao

import androidx.room.*
import br.com.nglauber.tdcapp.data.local.entities.Session
import io.reactivex.Flowable

@Dao
interface SessionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(session: Session): Long

    @Query("DELETE FROM Session WHERE id = :sessionId")
    fun deleteById(sessionId: Long): Int

    @Query("SELECT * FROM Session WHERE eventId = :eventId AND modalityId = :modalityId")
    fun sessionsByModality(eventId: Long, modalityId: Long): Flowable<List<Session>>
}