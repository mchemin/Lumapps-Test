package com.chemin.lumappstest

import android.app.Application
import com.chemin.lumappstest.di.application.AppComponent
import com.chemin.lumappstest.di.application.DaggerAppComponent

class LumappsTestApplication : Application() {
    val component: AppComponent by lazy {
        DaggerAppComponent
            .factory()
            .create(
                application = this,
                baseUrl = "https://randomuser.me"
            )
    }
}


fun Application.getAppComponent() =
    (this as? LumappsTestApplication)
        ?.component
        ?: throw IllegalStateException("This should only be called on LumappsTestApplication instance")
