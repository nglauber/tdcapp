package br.com.nglauber.tdcapp.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.nglauber.tdcapp.BuildConfig
import br.com.nglauber.tdcapp.data.remote.TdcRemoteRepository
import br.com.nglauber.tdcapp.data.remote.service.TdcAuthStore
import br.com.nglauber.tdcapp.data.remote.service.TdcWebServiceFactory
import br.com.nglauber.tdcapp.domain.executor.PostExecutionThread
import br.com.nglauber.tdcapp.domain.interactor.event.GetEvents
import br.com.nglauber.tdcapp.domain.interactor.modality.GetModalitiesByEvent
import br.com.nglauber.tdcapp.domain.interactor.session.GetSessionsByModality
import br.com.nglauber.tdcapp.domain.interactor.session.GetSpeakersBySession
import br.com.nglauber.tdcapp.presentation.mapper.EventMapper
import br.com.nglauber.tdcapp.presentation.mapper.ModalityMapper
import br.com.nglauber.tdcapp.presentation.mapper.SessionMapper
import br.com.nglauber.tdcapp.presentation.mapper.SpeakerMapper

// TODO Inject application and post execution thread
class AppViewModelFactory(
        private val app: Application,
        private val postExecutionThread: PostExecutionThread

) : ViewModelProvider.AndroidViewModelFactory(app) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        // TODO how to inject the repository?
        val repository = TdcRemoteRepository(
                TdcWebServiceFactory().makeTdcWebService(TdcAuthStore(app), BuildConfig.DEBUG)
        )
        // TODO how to fix these warnings?
        return when {
            modelClass.isAssignableFrom(EventsListViewModel::class.java) ->
                EventsListViewModel(GetEvents(repository, postExecutionThread), EventMapper) as T

            modelClass.isAssignableFrom(ModalityListViewModel::class.java) ->
                ModalityListViewModel(GetModalitiesByEvent(repository, postExecutionThread), ModalityMapper) as T

            modelClass.isAssignableFrom(SessionListViewModel::class.java) ->
                SessionListViewModel(GetSessionsByModality(repository, postExecutionThread), SessionMapper) as T

            modelClass.isAssignableFrom(SessionViewModel::class.java) ->
                SessionViewModel(GetSpeakersBySession(repository, postExecutionThread), SpeakerMapper) as T

            else ->
                throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}