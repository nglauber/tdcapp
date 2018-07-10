package br.com.nglauber.tdcapp.presentation.mapper

import br.com.nglauber.tdcapp.domain.model.Modality
import br.com.nglauber.tdcapp.presentation.model.ModalityBinding

class ModalityMapper : Mapper<Modality, ModalityBinding> {
    override fun parse(domain: Modality): ModalityBinding {
        return ModalityBinding(
                domain.id,
                domain.description,
                domain.urlSite,
                domain.active,
                domain.positionOnEvent,
                domain.slogan,
                domain.detailedDescription,
                domain.targetAudience,
                domain.topics,
                domain.prerequisites,
                domain.warning,
                domain.publishOnSite,
                domain.date
        )
    }
}