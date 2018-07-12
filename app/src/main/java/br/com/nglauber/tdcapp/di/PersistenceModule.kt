package br.com.nglauber.tdcapp.di

import br.com.nglauber.tdcapp.data.local.TdcLocalDataSource
import br.com.nglauber.tdcapp.BuildConfig
import br.com.nglauber.tdcapp.data.LocalDataSource
import br.com.nglauber.tdcapp.data.RemoteDataSource
import br.com.nglauber.tdcapp.data.TdcRepository
import br.com.nglauber.tdcapp.data.memory.InMemoryRepository
import br.com.nglauber.tdcapp.data.remote.TdcRemoteDataSource
import br.com.nglauber.tdcapp.data.remote.service.TdcAuthStore
import br.com.nglauber.tdcapp.data.remote.service.TdcWebServiceFactory
import br.com.nglauber.tdcapp.domain.repository.Repository
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
        TdcRemoteDataSource(tdcWebService = get()) as RemoteDataSource
        //InMemoryRepository() as RemoteDataSource
    }
    single {
        TdcLocalDataSource(context = get()) as LocalDataSource
    }
    single {
        TdcRepository(
                remoteDataSource = get(),
                localDataSource = get()
        ) as Repository
    }
}

