package br.com.nglauber.tdcapp.presentation

import androidx.lifecycle.*
import br.com.nglauber.tdcapp.domain.interactor.session.GetSpeakersBySession
import br.com.nglauber.tdcapp.presentation.mapper.SpeakerMapper
import br.com.nglauber.tdcapp.presentation.model.SessionBinding
import br.com.nglauber.tdcapp.presentation.model.SpeakerBinding
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SessionViewModel @Inject constructor(
        private val getSpeakersBySession: GetSpeakersBySession,
        private val speakerMapper: SpeakerMapper
) : ViewModel(), LifecycleObserver {

    var eventId: Int = 0
    var modalityId: Int = 0
    var sessionBinding: SessionBinding? = null

    private val disposables = CompositeDisposable()

    private val state: MutableLiveData<ViewState<Pair<SessionBinding, List<SpeakerBinding>>>> = MutableLiveData()

    fun getState(): LiveData<ViewState<Pair<SessionBinding, List<SpeakerBinding>>>> {
        return state
    }

    fun fetchSpeakersBySession(eventId: Int, modalityId: Int, session: SessionBinding) {
        state.postValue(ViewState(ViewState.Status.LOADING))
        getSpeakersBySession.execute(
                GetSpeakersBySession.Params(eventId, modalityId, session.id),
                { speakers ->
                    val list = speakers.map { speakerMapper.parse(it) }
                    state.postValue(
                            ViewState(ViewState.Status.SUCCESS, session to list)
                    )
                },
                { e ->
                    state.postValue(ViewState(ViewState.Status.ERROR, error = e))
                }
        )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun fetchIfNeeded() {
        if (state.value == null) {
            sessionBinding?.let {
                fetchSpeakersBySession(eventId, modalityId, it)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}