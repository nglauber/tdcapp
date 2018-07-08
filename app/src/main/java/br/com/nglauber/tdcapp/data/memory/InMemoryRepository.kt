package br.com.nglauber.tdcapp.data.memory

import br.com.nglauber.tdcapp.domain.model.*
import br.com.nglauber.tdcapp.domain.repository.TdcRepository
import io.reactivex.Observable
import javax.inject.Inject


/*
* This is just a sample class to demonstrate how to easily replace the repository without change
* anything else in the application.
* You must have to change the returned instance in PersistenceModule class.
*/
class InMemoryRepository @Inject constructor() : TdcRepository {
    override fun getEvents(): Observable<List<Event>> {
        return Observable.just(
                listOf(
                        Event(1, "TDC São Paulo 2018", null, true, false,
                                5, null, "2018-07-17", "2018-07-21")
                )
        )
    }

    override fun getModalitiesByEvent(eventId: Int): Observable<List<Modality>> {
        return Observable.just(
                listOf(
                        Modality(1, "Android", null, true,
                                1, null, "Trilha Android",
                                null, null, null,
                                null, false, "2018-07-18"),
                        Modality(2, "iOS", null, true,
                                1, null, "Trilha iOS",
                                null, null, null,
                                null, false, "2018-07-19")
                )
        )
    }

    override fun getSessionsByModality(eventId: Int, modalityId: Int): Observable<List<Session>> {
        return Observable.just(
                listOf(
                        Session(1, 1, 1, false,
                                "Teste 1", "Palestra de teste 1 | Trilha $modalityId",
                                0, "10:00"),
                        Session(2, 1, 1, false,
                                "Teste 2", "Palestra de teste 2 | Trilha $modalityId",
                                0, "11:00")
                )
        )
    }

    override fun getSpeakersBySession(eventId: Int, modalityId: Int, sessionId: Int): Observable<List<Speaker>> {
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