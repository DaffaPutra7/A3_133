package com.example.a3_133

import android.app.Application
import com.example.a3_133.repository.A3Container
import com.example.a3_133.repository.AppContainer

class A3Applications : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = A3Container()
    }
}