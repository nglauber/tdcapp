package br.com.nglauber.tdcapp.presentation.mapper

import br.com.nglauber.tdcapp.domain.model.Speaker
import br.com.nglauber.tdcapp.presentation.model.SpeakerBinding

class SpeakerMapper(
        private val memberMapper: MemberMapper,
        private val miniBioMapper: MiniBioMapper
) : Mapper<Speaker, SpeakerBinding> {
    override fun toDomain(presentation: SpeakerBinding): Speaker {
        return Speaker(
                presentation.id,
                memberMapper.toDomain(presentation.member),
                miniBioMapper.toDomain(presentation.miniBio)
        )
    }

    override fun fromDomain(domain: Speaker): SpeakerBinding {
        return SpeakerBinding(
                domain.id,
                memberMapper.fromDomain(domain.member),
                miniBioMapper.fromDomain(domain.miniBio)
        )
    }
}