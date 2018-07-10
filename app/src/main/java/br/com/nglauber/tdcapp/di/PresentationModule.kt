package br.com.nglauber.tdcapp.di

import br.com.nglauber.tdcapp.presentation.EventsListViewModel
import br.com.nglauber.tdcapp.presentation.ModalityListViewModel
import br.com.nglauber.tdcapp.presentation.SessionListViewModel
import br.com.nglauber.tdcapp.presentation.SessionViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


val presentationModule = module {

    viewModel { EventsListViewModel(get(), get()) }
    viewModel { ModalityListViewModel(get(), get()) }
    viewModel { SessionListViewModel(get(), get()) }
    viewModel { SessionViewModel(get(), get()) }
}