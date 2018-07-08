package br.com.nglauber.tdcapp.ui.inject

import android.app.Application
import br.com.nglauber.tdcapp.TdcApplication
import br.com.nglauber.tdcapp.ui.inject.module.ApplicationModule
import br.com.nglauber.tdcapp.ui.inject.module.PresentationModule
import br.com.nglauber.tdcapp.ui.inject.module.PersistenceModule
import br.com.nglauber.tdcapp.ui.inject.module.UiModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ApplicationModule::class,
    PresentationModule::class,
    PersistenceModule::class,
    UiModule::class
])
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(app: TdcApplication)
}