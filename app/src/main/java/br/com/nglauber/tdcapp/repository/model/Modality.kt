package br.com.nglauber.tdcapp.repository.model

data class Modality(
        val id: Int,
        val description: String,
        val urlSite: String?,
        val active: Boolean?,
        val positionOnEvent: Int,
        val slogan: String?,
        val detailedDescription: String?,
        val targetAudience: String?,
        val topics: String?,
        val prerequisites: String?,
        val warning: String?,
        val publishOnSite: Boolean?,
        val date: String
)