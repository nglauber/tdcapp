package br.com.nglauber.tdcapp.repository.remote.mapper

import br.com.nglauber.tdcapp.repository.model.Speaker
import br.com.nglauber.tdcapp.repository.remote.model.TdcSpeaker

object SpeakerMapper: Mapper<TdcSpeaker, Speaker> {
    override fun parse(remote: TdcSpeaker): Speaker {
        return Speaker(
                remote.id,
                MemberMapper.parse(remote.member),
                MiniBioMapper.parse(remote.miniBio)
        )
    }
}