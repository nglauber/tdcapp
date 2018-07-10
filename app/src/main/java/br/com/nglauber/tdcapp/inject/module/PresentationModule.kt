package br.com.nglauber.tdcapp.inject.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.nglauber.tdcapp.inject.ViewModelKey
import br.com.nglauber.tdcapp.presentation.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class PresentationModule {
    @Binds
    @IntoMap
    @ViewModelKey(EventsListViewModel::class)
    abstract fun eventsListViewModel(viewModel: EventsListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ModalityListViewModel::class)
    abstract fun modalityListViewModel(viewModel: ModalityListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SessionListViewModel::class)
    abstract fun sessionListViewModel(viewModel: SessionListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SessionViewModel::class)
    abstract fun sessionViewModel(viewModel: SessionViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}