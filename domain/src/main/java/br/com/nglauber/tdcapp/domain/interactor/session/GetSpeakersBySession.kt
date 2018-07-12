package br.com.nglauber.tdcapp.domain.interactor.session

import br.com.nglauber.tdcapp.domain.executor.PostExecutionThread
import br.com.nglauber.tdcapp.domain.interactor.ObservableUseCase
import br.com.nglauber.tdcapp.domain.model.Speaker
import br.com.nglauber.tdcapp.domain.repository.Repository
import io.reactivex.Observable

open class GetSpeakersBySession(
        private val repository: Repository,
        postExecutionThread: PostExecutionThread
) : ObservableUseCase<List<Speaker>, GetSpeakersBySession.Params>(postExecutionThread) {

    override fun buildUseCaseObservable(params: GetSpeakersBySession.Params?): Observable<List<Speaker>> {
        if (params == null) throw IllegalArgumentException("You must inform the event and modality ids.")
        return repository.getSpeakersBySession(params.eventId, params.modalityId, params.sessionId)
    }

    class Params(val eventId: Long, val modalityId: Long, val sessionId: Long)
}