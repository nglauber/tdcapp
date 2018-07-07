package br.com.nglauber.tdcapp.repository.remote.mapper

import br.com.nglauber.tdcapp.repository.model.Member
import br.com.nglauber.tdcapp.repository.remote.model.TdcMember

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