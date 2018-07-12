package br.com.nglauber.tdcapp.presentation.mapper

import br.com.nglauber.tdcapp.domain.model.Event
import br.com.nglauber.tdcapp.presentation.model.EventBiding

class EventMapper : Mapper<Event, EventBiding> {
    override fun toDomain(presentation: EventBiding): Event {
        return Event(
                presentation.id,
                presentation.description,
                presentation.key,
                presentation.active,
                presentation.free,
                presentation.days,
                presentation.online,
                presentation.startDate,
                presentation.endDate
        )
    }

    override fun fromDomain(domain: Event): EventBiding {
        return EventBiding(
                domain.id,
                domain.description,
                domain.key,
                domain.active,
                domain.free,
                domain.days,
                domain.online,
                domain.startDate,
                domain.endDate
        )
    }
}