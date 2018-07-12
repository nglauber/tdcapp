package br.com.nglauber.data.local.test

import br.com.nglauber.tdcapp.data.local.entities.Session
import br.com.nglauber.tdcapp.data.local.entities.Speaker
import br.com.nglauber.tdcapp.domain.model.Member
import br.com.nglauber.tdcapp.domain.model.MiniBio

object EntityDataFactory {

    fun makeSession() = Session(
            DataFactory.randomLong(),
            DataFactory.randomInt(),
            DataFactory.randomInt(),
            DataFactory.randomBoolean(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomInt(),
            DataFactory.randomString(),
            DataFactory.randomLong(),
            DataFactory.randomLong()
    )

    fun makeSessionsList(count: Int): List<Session> {
        val sessions = mutableListOf<Session>()
        repeat(count) {
            sessions.add(makeSession())
        }
        return sessions
    }

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