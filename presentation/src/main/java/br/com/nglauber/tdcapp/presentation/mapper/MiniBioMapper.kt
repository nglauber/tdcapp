package br.com.nglauber.tdcapp.presentation.mapper

import br.com.nglauber.tdcapp.domain.model.MiniBio
import br.com.nglauber.tdcapp.presentation.model.MiniBioBinding

class MiniBioMapper : Mapper<MiniBio, MiniBioBinding> {
    override fun parse(domain: MiniBio): MiniBioBinding {
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