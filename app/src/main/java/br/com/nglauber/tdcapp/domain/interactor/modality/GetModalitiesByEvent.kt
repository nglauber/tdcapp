package br.com.nglauber.tdcapp.domain.interactor.modality

import br.com.nglauber.tdcapp.domain.executor.PostExecutionThread
import br.com.nglauber.tdcapp.domain.interactor.ObservableUseCase
import br.com.nglauber.tdcapp.domain.model.Modality
import br.com.nglauber.tdcapp.domain.repository.TdcRepository
import io.reactivex.Observable
import javax.inject.Inject

open class GetModalitiesByEvent @Inject constructor(
        private val repository: TdcRepository,
        postExecutionThread: PostExecutionThread
) : ObservableUseCase<List<Modality>, Int>(postExecutionThread) {

    override fun buildUseCaseObservable(params: Int?): Observable<List<Modality>> {
        if (params == null) throw IllegalArgumentException("You must inform the event id.")
        return repository.getModalitiesByEvent(params)
    }
}