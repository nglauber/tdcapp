package br.com.nglauber.tdcapp.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import br.com.nglauber.tdcapp.data.local.entities.Session
import br.com.nglauber.tdcapp.data.local.entities.Speaker

class SessionWithSpeakers {
    @Embedded
    lateinit var session: Session
    @Relation(
            parentColumn = "id",
            entityColumn = "id"
    )
    lateinit var speakers: List<Speaker>
}