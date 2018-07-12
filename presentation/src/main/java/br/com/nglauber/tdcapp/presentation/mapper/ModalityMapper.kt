package br.com.nglauber.tdcapp.presentation.mapper

import br.com.nglauber.tdcapp.domain.model.Modality
import br.com.nglauber.tdcapp.presentation.model.ModalityBinding

class ModalityMapper : Mapper<Modality, ModalityBinding> {
    override fun toDomain(presentation: ModalityBinding): Modality {
        return Modality(
                presentation.id,
                presentation.description,
                presentation.urlSite,
                presentation.active,
                presentation.positionOnEvent,
                presentation.slogan,
                presentation.detailedDescription,
                presentation.targetAudience,
                presentation.topics,
                presentation.prerequisites,
                presentation.warning,
                presentation.publishOnSite,
                presentation.date
        )
    }

    override fun fromDomain(domain: Modality): ModalityBinding {
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