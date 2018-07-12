package br.com.nglauber.tdcapp.data.remote.test

import br.com.nglauber.tdcapp.data.remote.model.*

object RemoteDataFactory {
    fun makeEvent() = TdcEvent(
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

    fun makeEventsList(count: Int): List<TdcEvent> {
        val events = mutableListOf<TdcEvent>()
        repeat(count) {
            events.add(makeEvent())
        }
        return events
    }

    fun makeModality() = TdcModality(
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

    fun makeModalitiesList(count: Int): List<TdcModality> {
        val modalities = mutableListOf<TdcModality>()
        repeat(count) {
            modalities.add(makeModality())
        }
        return modalities
    }

    fun makeSession() = TdcSession(
            DataFactory.randomLong(),
            DataFactory.randomInt(),
            DataFactory.randomInt(),
            DataFactory.randomBoolean(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomInt(),
            DataFactory.randomString()
    )

    fun makeSessionsList(count: Int): List<TdcSession> {
        val sessions = mutableListOf<TdcSession>()
        repeat(count) {
            sessions.add(makeSession())
        }
        return sessions
    }

    fun makeMember() = TdcMember(
             DataFactory.randomInt(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString()
    )

    fun makeMiniBio() = TdcMiniBio(
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

    fun makeSpeaker() = TdcSpeaker(
            DataFactory.randomLong(),
            makeMember(),
            makeMiniBio()
    )

    fun makeSpeakersList(count: Int): List<TdcSpeaker> {
        val speakers = mutableListOf<TdcSpeaker>()
        repeat(count) {
            speakers.add(makeSpeaker())
        }
        return speakers
    }
}