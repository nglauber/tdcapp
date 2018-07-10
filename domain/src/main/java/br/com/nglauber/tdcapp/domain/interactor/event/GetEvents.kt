package br.com.nglauber.tdcapp.domain.interactor.event

import br.com.nglauber.tdcapp.domain.executor.PostExecutionThread
import br.com.nglauber.tdcapp.domain.interactor.ObservableUseCase
import br.com.nglauber.tdcapp.domain.model.Event
import br.com.nglauber.tdcapp.domain.repository.TdcRepository
import io.reactivex.Observable

open class GetEvents(
        private val repository: TdcRepository,
        postExecutionThread: PostExecutionThread
) : ObservableUseCase<List<Event>, Unit>(postExecutionThread) {

    override fun buildUseCaseObservable(params: Unit?): Observable<List<Event>> {
        return repository.getEvents()
                .map { eventList ->
                    eventList.sortedByDescending { it.id }
                }
    }
}