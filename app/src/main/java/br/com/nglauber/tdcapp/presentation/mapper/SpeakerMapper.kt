package br.com.nglauber.tdcapp.presentation.mapper

import br.com.nglauber.tdcapp.domain.model.Speaker
import br.com.nglauber.tdcapp.presentation.model.SpeakerBinding

object SpeakerMapper : Mapper<Speaker, SpeakerBinding> {
    override fun parse(domain: Speaker): SpeakerBinding {
        return SpeakerBinding(
                domain.id,
                MemberMapper.parse(domain.member),
                MiniBioMapper.parse(domain.miniBio)
        )
    }
}