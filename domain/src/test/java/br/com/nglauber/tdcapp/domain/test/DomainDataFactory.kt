package br.com.nglauber.tdcapp.domain.test

import br.com.nglauber.tdcapp.domain.model.*

object DomainDataFactory {
    fun makeEvent() = Event(
            DataFactory.randomLong(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomBoolean(),
            DataFactory.randomBoolean(),
            DataFactory.randomInt(),
            DataFactory.randomBoolean(),
            DataFactory.randomDateAsString(),
            DataFactory.randomDateAsString()
    )

    fun makeEventsList(count: Int): List<Event> {
        val events = mutableListOf<Event>()
        repeat(count) {
            events.add(makeEvent())
        }
        return events
    }

    fun makeModality() = Modality(
            DataFactory.randomLong(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomBoolean(),
            DataFactory.randomInt(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomBoolean(),
            DataFactory.randomDateAsString()
    )

    fun makeModalitiesList(count: Int): List<Modality> {
        val modalities = mutableListOf<Modality>()
        repeat(count) {
            modalities.add(makeModality())
        }
        return modalities
    }

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
            DataFactory.randomLong(),
            DataFactory.randomBoolean()
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