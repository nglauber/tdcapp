package br.com.nglauber.tdcapp.di

import br.com.nglauber.tdcapp.presentation.EventsListViewModel
import br.com.nglauber.tdcapp.presentation.ModalityListViewModel
import br.com.nglauber.tdcapp.presentation.SessionListViewModel
import br.com.nglauber.tdcapp.presentation.SessionViewModel
import br.com.nglauber.tdcapp.presentation.mapper.*
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


val presentationModule = module {
    factory { EventMapper() }
    factory { ModalityMapper() }
    factory { SessionMapper() }
    factory { MemberMapper() }
    factory { MiniBioMapper() }
    factory {
        SpeakerMapper(
                memberMapper = get(),
                miniBioMapper = get()
        )
    }
    viewModel {
        EventsListViewModel(
                getEvents = get(),
                mapper = get()
        )
    }
    viewModel {
        ModalityListViewModel(
                getModalitiesByEvent = get(),
                mapper = get()
        )
    }
    viewModel {
        SessionListViewModel(
                getSessions = get(),
                mapper = get()
        )
    }
    viewModel {
        SessionViewModel(
                getSpeakersBySession = get(),
                speakerMapper = get()
        )
    }
}