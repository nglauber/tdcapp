package br.com.nglauber.tdcapp.presentation

import androidx.lifecycle.*
import br.com.nglauber.tdcapp.domain.interactor.session.BookmarkSession
import br.com.nglauber.tdcapp.domain.interactor.session.GetSpeakersBySession
import br.com.nglauber.tdcapp.domain.interactor.session.UnbookmarkSession
import br.com.nglauber.tdcapp.presentation.mapper.SessionMapper
import br.com.nglauber.tdcapp.presentation.mapper.SpeakerMapper
import br.com.nglauber.tdcapp.presentation.model.SessionBinding
import br.com.nglauber.tdcapp.presentation.model.SpeakerBinding

class SessionViewModel(
        private val getSpeakersBySession: GetSpeakersBySession,
        private val bookmarkSession: BookmarkSession,
        private val unbookmarkSession: UnbookmarkSession,
        private val sessionMapper: SessionMapper,
        private val speakerMapper: SpeakerMapper
) : ViewModel(), LifecycleObserver {

    var eventId: Long = 0
    var modalityId: Long = 0
    var sessionBinding: SessionBinding? = null
    var speakersList: List<SpeakerBinding>? = null

    private val state: MutableLiveData<ViewState<Pair<SessionBinding, List<SpeakerBinding>>>> = MutableLiveData()

    fun getState(): LiveData<ViewState<Pair<SessionBinding, List<SpeakerBinding>>>> {
        return state
    }

    fun fetchSpeakersBySession(eventId: Long, modalityId: Long, session: SessionBinding) {
        state.postValue(ViewState(ViewState.Status.LOADING))
        getSpeakersBySession.execute(
                GetSpeakersBySession.Params(eventId, modalityId, session.id),
                { speakers ->
                    val list = speakers.map { speakerMapper.fromDomain(it) }
                    speakersList = list
                    state.postValue(
                            ViewState(ViewState.Status.SUCCESS, session to list)
                    )
                },
                { e ->
                    state.postValue(ViewState(ViewState.Status.ERROR, error = e))
                }
        )
    }

    fun toggleBookmarkSession() {
        val pair = sessionBinding to speakersList
        pair.let {
            val (session, speakers) = it
            if (session != null && speakers != null)
                if (session.bookmarked) {
                    val params = UnbookmarkSession.Params(sessionMapper.toDomain(session))
                    unbookmarkSession.execute(params,
                            {
                                val bookmarkedSession = session.copy(bookmarked = false)
                                sessionBinding = bookmarkedSession
                                state.postValue(
                                        ViewState(ViewState.Status.SUCCESS, bookmarkedSession to speakers)
                                )
                            },
                            { e ->
                                state.postValue(ViewState(ViewState.Status.ERROR, error = e))
                            }
                    )
                } else {
                    val params = BookmarkSession.Params(
                            sessionMapper.toDomain(session),
                            speakers.map { speakerMapper.toDomain(it) }
                    )
                    bookmarkSession.execute(params,
                            {
                                val unbookmarkedSession = session.copy(bookmarked = true)
                                sessionBinding = unbookmarkedSession
                                state.postValue(
                                        ViewState(ViewState.Status.SUCCESS, unbookmarkedSession to speakers)
                                )
                            },
                            { e ->
                                state.postValue(ViewState(ViewState.Status.ERROR, error = e))
                            }
                    )
                }
        }
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
        getSpeakersBySession.dispose()
    }
}