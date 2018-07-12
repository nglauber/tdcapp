package br.com.nglauber.tdcapp.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey


@Entity(tableName = "SessionSpeakerJoin",
        primaryKeys = ["sessionId", "speakerId"],
        foreignKeys = [
            ForeignKey(
                    entity = Session::class,
                    parentColumns = ["id"],
                    childColumns = ["sessionId"],
                    onDelete = ForeignKey.CASCADE
            ),
            ForeignKey(
                    entity = Speaker::class,
                    parentColumns = ["id"],
                    childColumns = ["speakerId"],
                    onDelete = ForeignKey.CASCADE
            )
        ]
)
class SessionSpeakerJoin(val sessionId: Long, val speakerId: Long)