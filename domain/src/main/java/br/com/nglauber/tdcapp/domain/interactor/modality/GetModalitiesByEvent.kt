package br.com.nglauber.tdcapp.domain.interactor.modality

import br.com.nglauber.tdcapp.domain.executor.PostExecutionThread
import br.com.nglauber.tdcapp.domain.interactor.ObservableUseCase
import br.com.nglauber.tdcapp.domain.model.Modality
import br.com.nglauber.tdcapp.domain.repository.Repository
import io.reactivex.Observable

open class GetModalitiesByEvent (
        private val repository: Repository,
        postExecutionThread: PostExecutionThread
) : ObservableUseCase<List<Modality>, Long>(postExecutionThread) {

    override fun buildUseCaseObservable(params: Long?): Observable<List<Modality>> {
        if (params == null) throw IllegalArgumentException("You must inform the event id.")
        return repository.getModalitiesByEvent(params)
    }
}