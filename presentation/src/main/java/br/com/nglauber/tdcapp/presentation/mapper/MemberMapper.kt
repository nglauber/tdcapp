package br.com.nglauber.tdcapp.presentation.mapper

import br.com.nglauber.tdcapp.domain.model.Member
import br.com.nglauber.tdcapp.presentation.model.MemberBinding

class MemberMapper : Mapper<Member, MemberBinding> {
    override fun toDomain(presentation: MemberBinding): Member {
        return Member(
                presentation.id,
                presentation.name,
                presentation.company,
                presentation.role,
                presentation.email,
                presentation.city,
                presentation.state,
                presentation.country
        )
    }

    override fun fromDomain(domain: Member): MemberBinding {
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