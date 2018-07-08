package br.com.nglauber.tdcapp

import android.app.Activity
import android.app.Application
import br.com.nglauber.tdcapp.ui.inject.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class TdcApplication: Application(), HasActivityInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        DaggerApplicationComponent
                .builder()
                .application(this)
                .build()
                .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return androidInjector
    }
}