package br.com.nglauber.tdcapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.nglauber.tdcapp.repository.TdcRepository
import br.com.nglauber.tdcapp.repository.model.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

//TODO Inject repository
class EventsListViewModel(private val repository: TdcRepository) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val state: MutableLiveData<ViewState<List<Event>>> = MutableLiveData()

    fun getState(): LiveData<ViewState<List<Event>>> {
        return state
    }

    fun fetchEvents() {
        state.postValue(ViewState(ViewState.Status.LOADING))
        disposables.add(repository.getEvents()
                .map { eventList ->
                    eventList.sortedByDescending { it.id }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { eventList ->
                            state.postValue(ViewState(ViewState.Status.SUCCESS, eventList))
                        },
                        { e ->
                            state.postValue(ViewState(ViewState.Status.ERROR, error = e))
                        }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}