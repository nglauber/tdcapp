package br.com.nglauber.tdcapp.di

import br.com.nglauber.tdcapp.domain.interactor.event.GetEvents
import br.com.nglauber.tdcapp.domain.interactor.modality.GetModalitiesByEvent
import br.com.nglauber.tdcapp.domain.interactor.session.GetSessionsByModality
import br.com.nglauber.tdcapp.domain.interactor.session.GetSpeakersBySession
import org.koin.dsl.module.module


val domainModule = module {
    single {
        GetEvents(
                repository = get(),
                postExecutionThread = get()
        )
    }
    single {
        GetModalitiesByEvent(
                repository = get(),
                postExecutionThread = get()
        )
    }
    single {
        GetSessionsByModality(
                repository = get(),
                postExecutionThread = get()
        )
    }
    single {
        GetSpeakersBySession(
                repository = get(),
                postExecutionThread = get()
        )
    }
}