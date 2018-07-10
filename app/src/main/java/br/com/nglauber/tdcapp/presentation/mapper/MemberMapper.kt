package br.com.nglauber.tdcapp.presentation.mapper

import br.com.nglauber.tdcapp.domain.model.Member
import br.com.nglauber.tdcapp.presentation.model.MemberBinding

class MemberMapper : Mapper<Member, MemberBinding> {
    override fun parse(domain: Member): MemberBinding {
        return MemberBinding(
                domain.id,
                domain.name,
                domain.company,
                domain.role,
                domain.email,
                domain.city,
                domain.state,
                domain.country
        )
    }
}