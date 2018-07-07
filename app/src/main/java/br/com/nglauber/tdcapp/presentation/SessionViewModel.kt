package br.com.nglauber.tdcapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.nglauber.tdcapp.repository.TdcRepository
import br.com.nglauber.tdcapp.repository.model.Session
import br.com.nglauber.tdcapp.repository.model.Speaker
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SessionViewModel(private val repository: TdcRepository) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val state: MutableLiveData<ViewState<Pair<Session, List<Speaker>>>> = MutableLiveData()

    fun getState(): LiveData<ViewState<Pair<Session, List<Speaker>>>> {
        return state
    }

    fun fetchSpeakersBySession(eventId: Int, modalityId: Int, session: Session) {
        state.postValue(ViewState(ViewState.Status.LOADING))
        disposables.add(repository.getSpeakersBySession(eventId, modalityId, session.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { speakers ->
                            state.postValue(
                                    ViewState(ViewState.Status.SUCCESS,session to speakers)
                            )
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