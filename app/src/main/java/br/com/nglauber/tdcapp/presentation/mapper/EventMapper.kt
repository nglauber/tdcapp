package br.com.nglauber.tdcapp.presentation.mapper

import br.com.nglauber.tdcapp.domain.model.Event
import br.com.nglauber.tdcapp.presentation.model.EventBiding

object EventMapper: Mapper<Event, EventBiding> {
    override fun parse(domain: Event): EventBiding {
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