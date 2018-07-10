package br.com.nglauber.tdcapp.di

import br.com.nglauber.tdcapp.BuildConfig
import br.com.nglauber.tdcapp.data.remote.TdcRemoteRepository
import br.com.nglauber.tdcapp.data.remote.service.TdcAuthStore
import br.com.nglauber.tdcapp.data.remote.service.TdcWebServiceFactory
import br.com.nglauber.tdcapp.domain.repository.TdcRepository
import br.com.nglauber.tdcapp.ui.auth.TdcAuthStoreImpl
import org.koin.dsl.module.module

val persistenceModule = module {

    single { TdcAuthStoreImpl(get()) as TdcAuthStore }
    single { TdcWebServiceFactory.makeTdcWebService(get(), BuildConfig.DEBUG) }
    single { TdcRemoteRepository(get()) as TdcRepository }
}

