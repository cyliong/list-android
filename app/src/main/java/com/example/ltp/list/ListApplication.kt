package com.example.ltp.list

import androidx.multidex.MultiDexApplication
import io.realm.Realm

class ListApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }

}