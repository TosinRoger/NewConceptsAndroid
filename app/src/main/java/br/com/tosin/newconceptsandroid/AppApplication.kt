package br.com.tosin.newconceptsandroid

import android.app.Application
import android.content.Context

class AppApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        var context: Context? = null
    }
}