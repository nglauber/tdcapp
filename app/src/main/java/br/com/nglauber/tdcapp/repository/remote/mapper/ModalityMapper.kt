package br.com.nglauber.tdcapp.repository.remote.mapper

import br.com.nglauber.tdcapp.repository.model.Modality
import br.com.nglauber.tdcapp.repository.remote.model.TdcModality

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