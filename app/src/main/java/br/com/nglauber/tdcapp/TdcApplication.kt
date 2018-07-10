package br.com.nglauber.tdcapp

import android.app.Application
import br.com.nglauber.tdcapp.di.androidModule
import br.com.nglauber.tdcapp.di.domainModule
import br.com.nglauber.tdcapp.di.persistenceModule
import br.com.nglauber.tdcapp.di.presentationModule
import org.koin.android.ext.android.startKoin

class TdcApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this,
                listOf(
                        androidModule,
                        persistenceModule,
                        domainModule,
                        presentationModule
                )
        )
    }
}