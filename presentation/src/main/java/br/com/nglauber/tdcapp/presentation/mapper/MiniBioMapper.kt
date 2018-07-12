package br.com.nglauber.tdcapp.presentation.mapper

import br.com.nglauber.tdcapp.domain.model.MiniBio
import br.com.nglauber.tdcapp.presentation.model.MiniBioBinding

class MiniBioMapper : Mapper<MiniBio, MiniBioBinding> {
    override fun toDomain(presentation: MiniBioBinding): MiniBio {
        return MiniBio(
                presentation.id,
                presentation.key,
                presentation.text,
                presentation.urlPhoto,
                presentation.urlTwitter,
                presentation.urlBlog,
                presentation.urlLinkedin,
                presentation.urlSite,
                presentation.urlGlobalcoders
        )
    }

    override fun fromDomain(domain: MiniBio): MiniBioBinding {
        return MiniBioBinding(
                domain.id,
                domain.key,
                domain.text,
                domain.urlPhoto,
                domain.urlTwitter,
                domain.urlBlog,
                domain.urlLinkedin,
                domain.urlSite,
                domain.urlGlobalcoders
        )
    }
}