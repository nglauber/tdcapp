package br.com.nglauber.tdcapp.di

import br.com.nglauber.tdcapp.BuildConfig
import br.com.nglauber.tdcapp.data.remote.TdcRemoteRepository
import br.com.nglauber.tdcapp.data.remote.service.TdcAuthStore
import br.com.nglauber.tdcapp.data.remote.service.TdcWebServiceFactory
import br.com.nglauber.tdcapp.domain.repository.TdcRepository
import br.com.nglauber.tdcapp.ui.auth.TdcAuthStoreImpl
import org.koin.dsl.module.module

val persistenceModule = module {
    single {
        TdcAuthStoreImpl(ctx = get()) as TdcAuthStore
    }
    single {
        TdcWebServiceFactory.makeTdcWebService(
                authStore = get(),
                isDebug = BuildConfig.DEBUG
        )
    }
    single {
        TdcRemoteRepository(tdcWebService = get()) as TdcRepository
    }
}

