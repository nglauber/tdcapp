package br.com.nglauber.tdcapp.domain.interactor.session

import br.com.nglauber.tdcapp.domain.executor.PostExecutionThread
import br.com.nglauber.tdcapp.domain.interactor.ObservableUseCase
import br.com.nglauber.tdcapp.domain.model.Session
import br.com.nglauber.tdcapp.domain.repository.TdcRepository
import io.reactivex.Observable
import javax.inject.Inject

open class GetSessionsByModality @Inject constructor(
        private val repository: TdcRepository,
        postExecutionThread: PostExecutionThread
) : ObservableUseCase<List<Session>, GetSessionsByModality.Params>(postExecutionThread) {

    override fun buildUseCaseObservable(params: GetSessionsByModality.Params?): Observable<List<Session>> {
        if (params == null) throw IllegalArgumentException("You must inform the event and modality ids.")
        return repository.getSessionsByModality(params.eventId, params.modalityId)
                .map { sessionList ->
                    sessionList.sortedBy { it.time }
                }
    }

    class Params(val eventId: Int, val modalityId: Int)
}