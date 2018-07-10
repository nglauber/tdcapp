package br.com.nglauber.tdcapp.presentation

import androidx.lifecycle.*
import br.com.nglauber.tdcapp.domain.interactor.event.GetEvents
import br.com.nglauber.tdcapp.presentation.mapper.EventMapper
import br.com.nglauber.tdcapp.presentation.model.EventBiding

class EventsListViewModel(
        private val getEvents: GetEvents,
        private val mapper: EventMapper
) : ViewModel(), LifecycleObserver {

    private val state: MutableLiveData<ViewState<List<EventBiding>>> = MutableLiveData()

    fun getState(): LiveData<ViewState<List<EventBiding>>> {
        return state
    }

    fun fetchEvents() {
        state.postValue(ViewState(ViewState.Status.LOADING))
        getEvents.execute(null,
                { eventList ->
                    val list = eventList.map { mapper.parse(it) }
                    state.postValue(ViewState(ViewState.Status.SUCCESS, list))
                },
                { e ->
                    state.postValue(ViewState(ViewState.Status.ERROR, error = e))
                }
        )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun fetchIfNeeded() {
        if (state.value == null) {
            fetchEvents()
        }
    }

    override fun onCleared() {
        super.onCleared()
        getEvents.dispose()
    }
}