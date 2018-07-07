package br.com.nglauber.tdcapp.repository.model

data class MiniBio(
        val id: Int,
        val key: String?,
        val text: String?,
        val urlPhoto: String?,
        val urlTwitter: String?,
        val urlBlog: String?,
        val urlLinkedin: String?,
        val urlSite: String?,
        val urlGlobalcoders: String?
)