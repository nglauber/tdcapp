package br.com.nglauber.tdcapp.data.local.dao

import androidx.room.*
import br.com.nglauber.tdcapp.data.local.entities.Speaker
import io.reactivex.Maybe

@Dao
interface SpeakerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(speaker: Speaker): Long

    @Query("SELECT * FROM Speaker WHERE id = :id")
    fun speakerById(id: Long): Maybe<Speaker>
}