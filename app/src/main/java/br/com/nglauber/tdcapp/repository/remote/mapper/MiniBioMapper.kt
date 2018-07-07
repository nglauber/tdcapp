package br.com.nglauber.tdcapp.repository.remote.mapper

import br.com.nglauber.tdcapp.repository.model.MiniBio
import br.com.nglauber.tdcapp.repository.remote.model.TdcMiniBio

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