package br.com.nglauber.tdcapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.nglauber.tdcapp.repository.TdcRepository
import br.com.nglauber.tdcapp.repository.model.Modality
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

//TODO Inject repository
class ModalityListViewModel(private val repository: TdcRepository) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val state: MutableLiveData<ViewState<Map<String, List<Modality>>>> = MutableLiveData()

    fun getState(): LiveData<ViewState<Map<String,List<Modality>>>> {
        return state
    }

    fun fetchModalities(eventId: Int) {
        state.postValue(ViewState(ViewState.Status.LOADING))
        disposables.add(repository.getModalitiesByEvent(eventId)
                .map { modalityList ->
                    modalityList.sortedWith(
                            compareBy({ it.date }, { it.positionOnEvent })
                    ).groupBy { it.date }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { modalityList ->
                            state.postValue(ViewState(ViewState.Status.SUCCESS, modalityList))
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