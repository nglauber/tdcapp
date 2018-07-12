package br.com.nglauber.tdcapp.domain.interactor.session

import br.com.nglauber.tdcapp.domain.executor.PostExecutionThread
import br.com.nglauber.tdcapp.domain.interactor.CompletableUseCase
import br.com.nglauber.tdcapp.domain.model.Session
import br.com.nglauber.tdcapp.domain.model.Speaker
import br.com.nglauber.tdcapp.domain.repository.Repository
import io.reactivex.Completable

open class UnbookmarkSession(
        private val repository: Repository,
        postExecutionThread: PostExecutionThread
) : CompletableUseCase<UnbookmarkSession.Params>(postExecutionThread) {

    override fun buildUseCaseCompletable(params: Params?): Completable {
        if (params == null) throw IllegalArgumentException("Params can't be null")
        return repository.unbookmarkSession(params.session)
    }

    data class Params(val session: Session)
}