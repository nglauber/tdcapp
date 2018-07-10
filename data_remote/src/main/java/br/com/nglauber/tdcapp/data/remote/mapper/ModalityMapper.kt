package br.com.nglauber.tdcapp.data.remote.mapper

import br.com.nglauber.tdcapp.data.remote.model.TdcModality
import br.com.nglauber.tdcapp.domain.model.Modality

object ModalityMapper: Mapper<TdcModality, Modality> {
    override fun parse(remote: TdcModality): Modality {
        return Modality(
                remote.id,
                remote.description,
                remote.urlSite,
                remote.active,
                remote.positionOnEvent,
                remote.slogan,
                remote.detailedDescription,
                remote.targetAudience,
                remote.topics,
                remote.prerequisites,
                remote.warning,
                remote.publishOnSite,
                remote.date
        )
    }
}