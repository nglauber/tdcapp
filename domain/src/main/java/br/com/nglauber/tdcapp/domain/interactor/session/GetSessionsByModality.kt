package br.com.nglauber.tdcapp.domain.interactor.session

import br.com.nglauber.tdcapp.domain.executor.PostExecutionThread
import br.com.nglauber.tdcapp.domain.interactor.ObservableUseCase
import br.com.nglauber.tdcapp.domain.model.Session
import br.com.nglauber.tdcapp.domain.repository.Repository
import io.reactivex.Observable

open class GetSessionsByModality (
        private val repository: Repository,
        postExecutionThread: PostExecutionThread
) : ObservableUseCase<List<Session>, GetSessionsByModality.Params>(postExecutionThread) {

    override fun buildUseCaseObservable(params: GetSessionsByModality.Params?): Observable<List<Session>> {
        if (params == null) throw IllegalArgumentException("You must inform the event and modality ids.")
        return repository.getSessionsByModality(params.eventId, params.modalityId)
                .map { sessionList ->
                    sessionList.sortedBy { it.time }
                }
    }

    class Params(val eventId: Long, val modalityId: Long)
}