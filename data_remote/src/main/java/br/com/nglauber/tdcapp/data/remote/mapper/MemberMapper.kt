package br.com.nglauber.tdcapp.data.remote.mapper

import br.com.nglauber.tdcapp.data.remote.model.TdcMember
import br.com.nglauber.tdcapp.domain.model.Member

object MemberMapper: Mapper<TdcMember, Member> {
    override fun parse(remote: TdcMember): Member {
        return Member(
                remote.id,
                remote.name,
                remote.company,
                remote.role,
                remote.email,
                remote.city,
                remote.state,
                remote.country
        )
    }
}