package br.com.nglauber.tdcapp.data.local.mapper

import br.com.nglauber.tdcapp.data.local.entities.Session as SessionEntity
import br.com.nglauber.tdcapp.domain.model.Session as SessionDomain

object SessionMapper: Mapper<SessionEntity, SessionDomain> {
    override fun toDomain(entity: SessionEntity): SessionDomain {
        return SessionDomain(
                entity.id,
                entity.slot,
                entity.order,
                entity.activated,
                entity.title,
                entity.description,
                entity.type,
                entity.time,
                entity.eventId,
                entity.modalityId,
                true // if it's in the database, it's bookmarked
        )
    }

    override fun fromDomain(domain: SessionDomain): SessionEntity {
        return SessionEntity(
                domain.id,
                domain.slot,
                domain.order,
                domain.activated,
                domain.title,
                domain.description,
                domain.type,
                domain.time,
                domain.eventId,
                domain.modalityId
        )
    }
}