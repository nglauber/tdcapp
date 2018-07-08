package br.com.nglauber.tdcapp.presentation.mapper

import br.com.nglauber.tdcapp.domain.model.Speaker
import br.com.nglauber.tdcapp.presentation.model.SpeakerBinding
import javax.inject.Inject

class SpeakerMapper @Inject constructor(
        private val memberMapper: MemberMapper,
        private val miniBioMapper: MiniBioMapper
) : Mapper<Speaker, SpeakerBinding> {
    override fun parse(domain: Speaker): SpeakerBinding {
        return SpeakerBinding(
                domain.id,
                memberMapper.parse(domain.member),
                miniBioMapper.parse(domain.miniBio)
        )
    }
}