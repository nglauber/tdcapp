package br.com.nglauber.tdcapp.ui.inject.module

import br.com.nglauber.tdcapp.BuildConfig
import br.com.nglauber.tdcapp.data.memory.InMemoryRepository
import br.com.nglauber.tdcapp.data.remote.TdcRemoteRepository
import br.com.nglauber.tdcapp.data.remote.service.TdcAuthStore
import br.com.nglauber.tdcapp.data.remote.service.TdcWebService
import br.com.nglauber.tdcapp.data.remote.service.TdcWebServiceFactory
import br.com.nglauber.tdcapp.domain.repository.TdcRepository
import br.com.nglauber.tdcapp.ui.auth.TdcAuthStoreImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class PersistenceModule {
    @Binds
    abstract fun bindAuthStore(authStore: TdcAuthStoreImpl): TdcAuthStore

    @Binds
    abstract fun bindRemoteRepository(remoteRepository: TdcRemoteRepository): TdcRepository

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun providesTdcWebService(authStore: TdcAuthStore): TdcWebService {
            return TdcWebServiceFactory().makeTdcWebService(authStore, BuildConfig.DEBUG)
        }
    }
}