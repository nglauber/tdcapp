package br.com.nglauber.tdcapp.ui.executor

import br.com.nglauber.tdcapp.domain.executor.PostExecutionThread
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class UiThread @Inject constructor() : PostExecutionThread {
    override val scheduler: Scheduler
        get() = AndroidSchedulers.mainThread()
}