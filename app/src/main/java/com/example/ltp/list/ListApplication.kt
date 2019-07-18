package com.example.ltp.list

import android.app.Application
import io.realm.Realm

class ListApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }

}