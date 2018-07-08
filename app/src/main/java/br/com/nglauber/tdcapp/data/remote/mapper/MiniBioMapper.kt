package br.com.nglauber.tdcapp.data.remote.mapper

import br.com.nglauber.tdcapp.data.remote.model.TdcMiniBio
import br.com.nglauber.tdcapp.domain.model.MiniBio
import br.com.nglauber.tdcapp.presentation.mapper.Mapper

object MiniBioMapper: Mapper<TdcMiniBio, MiniBio> {
    override fun parse(remote: TdcMiniBio): MiniBio {
        return MiniBio(
                remote.id,
                remote.key,
                remote.text,
                remote.urlPhoto,
                remote.urlTwitter,
                remote.urlBlog,
                remote.urlLinkedin,
                remote.urlSite,
                remote.urlGlobalcoders
        )
    }
}