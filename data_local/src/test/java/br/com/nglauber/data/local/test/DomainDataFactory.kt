package br.com.nglauber.data.local.test

import br.com.nglauber.tdcapp.domain.model.Member
import br.com.nglauber.tdcapp.domain.model.MiniBio
import br.com.nglauber.tdcapp.domain.model.Session
import br.com.nglauber.tdcapp.domain.model.Speaker

object DomainDataFactory {

    fun makeSession(bookmarked: Boolean) = Session(
            DataFactory.randomLong(),
            DataFactory.randomInt(),
            DataFactory.randomInt(),
            DataFactory.randomBoolean(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomInt(),
            DataFactory.randomString(),
            DataFactory.randomLong(),
            DataFactory.randomLong(),
            bookmarked
    )

    fun makeMember() = Member(
            DataFactory.randomInt(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString()
    )

    fun makeMiniBio() = MiniBio(
            DataFactory.randomInt(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString()
    )

    fun makeSpeaker() = Speaker(
            DataFactory.randomLong(),
            makeMember(),
            makeMiniBio()
    )

    fun makeSpeakersList(count: Int): List<Speaker> {
        val speakers = mutableListOf<Speaker>()
        repeat(count) {
            speakers.add(makeSpeaker())
        }
        return speakers
    }
}