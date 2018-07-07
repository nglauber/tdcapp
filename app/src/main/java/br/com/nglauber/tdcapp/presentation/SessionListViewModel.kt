package br.com.nglauber.tdcapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.nglauber.tdcapp.repository.TdcRepository
import br.com.nglauber.tdcapp.repository.model.Session
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SessionListViewModel(private val repository: TdcRepository) : ViewModel() {
    var eventId: Int = 0
    var modalityId: Int = 0

    private val disposables = CompositeDisposable()

    private val state: MutableLiveData<ViewState<List<Session>>> = MutableLiveData()

    fun getState(): LiveData<ViewState<List<Session>>> {
        return state
    }

    fun fetchSessionsByModality(eventId: Int, modalityId: Int) {
        this.eventId = eventId
        this.modalityId = modalityId

        state.postValue(ViewState(ViewState.Status.LOADING))

        disposables.add(repository.getSessionsByModality(eventId, modalityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    sessionList -> sessionList.sortedBy { it.time }
                }
                .subscribe(
                        { sessionList ->
                            state.postValue(ViewState(ViewState.Status.SUCCESS, sessionList))
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