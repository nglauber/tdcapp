package br.com.nglauber.tdcapp.data.remote.mapper

import br.com.nglauber.tdcapp.data.remote.model.TdcSpeaker
import br.com.nglauber.tdcapp.domain.model.Speaker

object SpeakerMapper: Mapper<TdcSpeaker, Speaker> {
    override fun parse(remote: TdcSpeaker): Speaker {
        return Speaker(
                remote.id,
                MemberMapper.parse(remote.member),
                MiniBioMapper.parse(remote.miniBio)
        )
    }
}