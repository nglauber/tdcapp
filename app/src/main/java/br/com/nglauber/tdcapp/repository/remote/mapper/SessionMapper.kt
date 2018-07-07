package br.com.nglauber.tdcapp.repository.remote.mapper

import br.com.nglauber.tdcapp.repository.model.Session
import br.com.nglauber.tdcapp.repository.remote.model.TdcSession

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