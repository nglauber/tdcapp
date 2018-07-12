package br.com.nglauber.tdcapp.presentation.mapper

import br.com.nglauber.tdcapp.domain.model.Session
import br.com.nglauber.tdcapp.presentation.model.SessionBinding

class SessionMapper : Mapper<Session, SessionBinding> {
    override fun toDomain(presentation: SessionBinding): Session {
        return Session(
                presentation.id,
                presentation.slot,
                presentation.order,
                presentation.activated,
                presentation.title,
                presentation.description,
                presentation.type,
                presentation.time,
                presentation.eventId,
                presentation.modalityId,
                presentation.bookmarked
        )
    }

    override fun fromDomain(domain: Session): SessionBinding {
        return SessionBinding(
                domain.id,
                domain.slot,
                domain.order,
                domain.activated,
                domain.title,
                domain.description,
                domain.type,
                domain.time,
                domain.eventId,
                domain.modalityId,
                domain.bookmarked
        )
    }
}