package br.com.nglauber.tdcapp.inject.module

import br.com.nglauber.tdcapp.domain.executor.PostExecutionThread
import br.com.nglauber.tdcapp.ui.activity.EventsListActivity
import br.com.nglauber.tdcapp.ui.activity.ModalityListActivity
import br.com.nglauber.tdcapp.ui.activity.SessionActivity
import br.com.nglauber.tdcapp.ui.activity.SessionListActivity
import br.com.nglauber.tdcapp.ui.executor.UiThread
import br.com.nglauber.tdcapp.ui.fragment.ModalityListFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UiModule {
    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread

    @ContributesAndroidInjector
    abstract fun contributesEventsListActivity(): EventsListActivity

    @ContributesAndroidInjector
    abstract fun contributesModalitiesListActivity(): ModalityListActivity

    @ContributesAndroidInjector
    abstract fun contributesModalityListFragment(): ModalityListFragment

    @ContributesAndroidInjector
    abstract fun contributesSessionsListActivity(): SessionListActivity

    @ContributesAndroidInjector
    abstract fun contributesSessionActivity(): SessionActivity
}