package br.com.nglauber.tdcapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.nglauber.tdcapp.data.local.dao.SessionDao
import br.com.nglauber.tdcapp.data.local.dao.SessionSpeakerJoinDao
import br.com.nglauber.tdcapp.data.local.dao.SpeakerDao
import br.com.nglauber.tdcapp.data.local.entities.Session
import br.com.nglauber.tdcapp.data.local.entities.SessionSpeakerJoin
import br.com.nglauber.tdcapp.data.local.entities.Speaker

@Database(
        entities = [
            Session::class,
            Speaker::class,
            SessionSpeakerJoin::class
        ],
        version = 1
)
abstract class TdcDatabase : RoomDatabase() {
    abstract fun sessionDao(): SessionDao
    abstract fun speakerDao(): SpeakerDao
    abstract fun sessionSpeakerJoinDao(): SessionSpeakerJoinDao
}