package br.com.nglauber.tdcapp.data.local.mapper

import br.com.nglauber.tdcapp.data.local.entities.Speaker as SpeakerEntity
import br.com.nglauber.tdcapp.domain.model.Speaker as SpeakerDomain

object SpeakerMapper : Mapper<SpeakerEntity, SpeakerDomain> {
    override fun toDomain(entity: SpeakerEntity): SpeakerDomain {
        return SpeakerDomain(
                entity.id,
                entity.member,
                entity.miniBio
        )
    }

    override fun fromDomain(domain: SpeakerDomain): SpeakerEntity {
        return SpeakerEntity(
                domain.id,
                domain.member,
                domain.miniBio
        )
    }
}