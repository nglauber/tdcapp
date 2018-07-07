package br.com.nglauber.tdcapp.repository.remote.mapper

import br.com.nglauber.tdcapp.repository.model.Event
import br.com.nglauber.tdcapp.repository.remote.model.TdcEvent

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