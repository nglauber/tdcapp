package br.com.nglauber.tdcapp.data.remote.mapper

import br.com.nglauber.tdcapp.data.remote.model.TdcSession
import br.com.nglauber.tdcapp.domain.model.Session
import br.com.nglauber.tdcapp.presentation.mapper.Mapper

object SessionMapper: Mapper<TdcSession, Session> {
    override fun parse(remote: TdcSession): Session {
        return Session(
                remote.id,
                remote.slot,
                remote.order,
                remote.activated,
                remote.title,
                remote.description,
                remote.type,
                remote.time
        )
    }
}