package br.com.nglauber.tdcapp.data.remote.mapper

import br.com.nglauber.tdcapp.data.remote.model.TdcEvent
import br.com.nglauber.tdcapp.domain.model.Event
import br.com.nglauber.tdcapp.presentation.mapper.Mapper

object EventMapper: Mapper<TdcEvent, Event> {
    override fun parse(remote: TdcEvent): Event {
        return Event(
                remote.id,
                remote.description,
                remote.key,
                remote.active,
                remote.free,
                remote.days,
                remote.online,
                remote.startDate,
                remote.endDate
        )
    }
}