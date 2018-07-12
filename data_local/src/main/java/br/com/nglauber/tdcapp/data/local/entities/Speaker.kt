package br.com.nglauber.tdcapp.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.nglauber.tdcapp.domain.model.Member
import br.com.nglauber.tdcapp.domain.model.MiniBio

@Entity
data class Speaker(
        @PrimaryKey(autoGenerate = false)
        val id: Long,
        @Embedded(prefix = "member_")
        val member: Member,
        @Embedded(prefix = "minibio_")
        val miniBio: MiniBio
)