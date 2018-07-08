package br.com.nglauber.tdcapp.domain.interactor.session

import br.com.nglauber.tdcapp.domain.executor.PostExecutionThread
import br.com.nglauber.tdcapp.domain.interactor.ObservableUseCase
import br.com.nglauber.tdcapp.domain.model.Speaker
import br.com.nglauber.tdcapp.domain.repository.TdcRepository
import io.reactivex.Observable
import javax.inject.Inject

open class GetSpeakersBySession @Inject constructor(
        private val repository: TdcRepository,
        postExecutionThread: PostExecutionThread
) : ObservableUseCase<List<Speaker>, GetSpeakersBySession.Params>(postExecutionThread) {

    override fun buildUseCaseObservable(params: GetSpeakersBySession.Params?): Observable<List<Speaker>> {
        if (params == null) throw IllegalArgumentException("You must inform the event and modality ids.")
        return repository.getSpeakersBySession(params.eventId, params.modalityId, params.sessionId)
    }

    class Params(val eventId: Int, val modalityId: Int, val sessionId: Int)
}