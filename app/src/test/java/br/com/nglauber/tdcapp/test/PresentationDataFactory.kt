package br.com.nglauber.tdcapp.test

import br.com.nglauber.tdcapp.domain.model.*
import br.com.nglauber.tdcapp.presentation.model.*

object PresentationDataFactory {
    fun makeEvent() = EventBiding(
            DataFactory.randomInt(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomBoolean(),
            DataFactory.randomBoolean(),
            DataFactory.randomInt(),
            DataFactory.randomBoolean(),
            DataFactory.randomDateAsString(),
            DataFactory.randomDateAsString()
    )

    fun makeEventsList(count: Int): List<EventBiding> {
        val events = mutableListOf<EventBiding>()
        repeat(count) {
            events.add(makeEvent())
        }
        return events
    }

    fun makeModality() = ModalityBinding(
            DataFactory.randomInt(),
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

    fun makeModalitiesList(count: Int): List<ModalityBinding> {
        val modalities = mutableListOf<ModalityBinding>()
        repeat(count) {
            modalities.add(makeModality())
        }
        return modalities
    }

    fun makeSession() = SessionBinding(
            DataFactory.randomInt(),
            DataFactory.randomInt(),
            DataFactory.randomInt(),
            DataFactory.randomBoolean(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomInt(),
            DataFactory.randomString()
    )

    fun makeSessionsList(count: Int): List<SessionBinding> {
        val sessions = mutableListOf<SessionBinding>()
        repeat(count) {
            sessions.add(makeSession())
        }
        return sessions
    }

    fun makeMember() = MemberBinding(
            DataFactory.randomInt(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString()
    )

    fun makeMiniBio() = MiniBioBinding(
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

    fun makeSpeaker() = SpeakerBinding(
            DataFactory.randomInt(),
            makeMember(),
            makeMiniBio()
    )

    fun makeSpeakersList(count: Int): List<SpeakerBinding> {
        val speakers = mutableListOf<SpeakerBinding>()
        repeat(count) {
            speakers.add(makeSpeaker())
        }
        return speakers
    }
}