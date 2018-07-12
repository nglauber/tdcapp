package br.com.nglauber.tdcapp.domain.interactor.session

import br.com.nglauber.tdcapp.domain.executor.PostExecutionThread
import br.com.nglauber.tdcapp.domain.interactor.CompletableUseCase
import br.com.nglauber.tdcapp.domain.model.Session
import br.com.nglauber.tdcapp.domain.model.Speaker
import br.com.nglauber.tdcapp.domain.repository.Repository
import io.reactivex.Completable

open class BookmarkSession (
        private val repository: Repository,
        postExecutionThread: PostExecutionThread
): CompletableUseCase<BookmarkSession.Params>(postExecutionThread) {

    override fun buildUseCaseCompletable(params: Params?): Completable {
        if (params == null) throw IllegalArgumentException("Params can't be null")
        return repository.bookmarkSession(params.session, params.speakers)
    }

    data class Params(
            val session: Session,
            val speakers: List<Speaker>
    )
}