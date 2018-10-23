package br.com.tosin.newconceptsandroid

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.squareup.leakcanary.LeakCanary

class AppApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        context = this

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
    }
}