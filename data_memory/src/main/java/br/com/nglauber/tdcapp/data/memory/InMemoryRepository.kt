package br.com.nglauber.tdcapp.data.memory

import br.com.nglauber.tdcapp.data.RemoteDataSource
import br.com.nglauber.tdcapp.domain.model.*
import io.reactivex.Observable

/*
* This is just a sample class to demonstrate how to easily replace the repository without change
* anything else in the application.
* You must have to change the returned instance in PersistenceModule class.
*/
class InMemoryRepository : RemoteDataSource {
    override fun getEvents(): Observable<List<Event>> {
        return Observable.just(
                listOf(
                        Event(1, "TDC São Paulo 2018", null, true, false,
                                5, null, "2018-07-17", "2018-07-21")
                )
        )
    }

    override fun getModalitiesByEvent(eventId: Long): Observable<List<Modality>> {
        return Observable.just(
                listOf(
                        Modality(1, "Android", null, true,
                                1, null, "Trilha Android",
                                null, null, null,
                                null, false, "18/07/18"),
                        Modality(2, "iOS", null, true,
                                1, null, "Trilha iOS",
                                null, null, null,
                                null, false, "19/07/18")
                )
        )
    }

    override fun getSessionsByModality(eventId: Long, modalityId: Long): Observable<List<Session>> {
        return Observable.just(
                listOf(
                        Session(1 + (10_000 * eventId) + (100_000 * modalityId), 1, 1, false,
                                "Teste 1", "Palestra de teste 1 | Trilha $modalityId",
                                0, "10:00", eventId, modalityId, false),
                        Session(2 + (10_000 * eventId) + (100_000 * modalityId), 1, 1, false,
                                "Teste 2", "Palestra de teste 2 | Trilha $modalityId",
                                0, "11:00", eventId, modalityId, false)
                )
        )
    }

    override fun getSpeakersBySession(eventId: Long, modalityId: Long, sessionId: Long): Observable<List<Speaker>> {
        return Observable.just(
                listOf(
                        Speaker(1,
                                Member(1, "Nelson Glauber", "Mokriya",
                                        "Android Developer", "nglaubervasc@gmail.com",
                                        "Recife", "PE", "Brasil"
                                ),
                                MiniBio(1, null, "Software developer",
                                        "https://pbs.twimg.com/profile_images/836560780422164480/vuClsC2w_400x400.jpg",
                                        "http://twitter.com/nglauber", "http://www.nglauber.com.br", null, null, null)
                        )
                )
        )
    }
}