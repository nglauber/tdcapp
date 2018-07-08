package br.com.nglauber.tdcapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.nglauber.tdcapp.domain.interactor.session.GetSessionsByModality
import br.com.nglauber.tdcapp.presentation.mapper.SessionMapper
import br.com.nglauber.tdcapp.presentation.model.SessionBinding

class SessionListViewModel(
        private val getSessions: GetSessionsByModality,
        private val mapper: SessionMapper
) : ViewModel() {
    var eventId: Int = 0
    var modalityId: Int = 0

    private val state: MutableLiveData<ViewState<List<SessionBinding>>> = MutableLiveData()

    fun getState(): LiveData<ViewState<List<SessionBinding>>> {
        return state
    }

    fun fetchSessionsByModality(eventId: Int, modalityId: Int) {
        this.eventId = eventId
        this.modalityId = modalityId

        state.postValue(ViewState(ViewState.Status.LOADING))

        getSessions.execute(
                GetSessionsByModality.Params(eventId, modalityId),
                { sessionList ->
                    val list = sessionList.map { mapper.parse(it) }
                    state.postValue(ViewState(ViewState.Status.SUCCESS, list))
                },
                { e ->
                    state.postValue(ViewState(ViewState.Status.ERROR, error = e))
                }
        )
    }

    override fun onCleared() {
        super.onCleared()
        getSessions.dispose()
    }
}