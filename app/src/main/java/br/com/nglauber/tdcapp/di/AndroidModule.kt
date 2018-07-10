package br.com.nglauber.tdcapp.di

import br.com.nglauber.tdcapp.domain.executor.PostExecutionThread
import br.com.nglauber.tdcapp.ui.executor.UiThread
import org.koin.dsl.module.module


val androidModule = module {
    single { this }
    single { UiThread() as PostExecutionThread }
}