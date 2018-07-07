package br.com.nglauber.tdcapp.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.nglauber.tdcapp.BuildConfig
import br.com.nglauber.tdcapp.repository.remote.TdcRemoteRepository
import br.com.nglauber.tdcapp.repository.remote.service.TdcAuthStore
import br.com.nglauber.tdcapp.repository.remote.service.TdcWebServiceFactory

// TODO Inject application
class AppViewModelFactory(val app: Application)
    : ViewModelProvider.AndroidViewModelFactory(app) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        // TODO how to inject this context? and teh repository?
        val repository = TdcRemoteRepository(
                TdcWebServiceFactory().makeTdcWebService(TdcAuthStore(app), BuildConfig.DEBUG)
        )
        // TODO how to fix this warning?
        return when {
            modelClass.isAssignableFrom(EventsListViewModel::class.java) ->
                EventsListViewModel(repository) as T
            modelClass.isAssignableFrom(ModalityListViewModel::class.java) ->
                ModalityListViewModel(repository) as T
            modelClass.isAssignableFrom(SessionListViewModel::class.java) ->
                SessionListViewModel(repository) as T
            modelClass.isAssignableFrom(SessionViewModel::class.java) ->
                SessionViewModel(repository) as T
            else ->
                throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}