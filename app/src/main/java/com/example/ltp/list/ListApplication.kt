package com.example.ltp.list

import androidx.multidex.MultiDexApplication
import com.example.ltp.list.db.Migration
import io.realm.Realm
import io.realm.RealmConfiguration

class ListApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .schemaVersion(1)
            .migration(Migration())
            .build()
        Realm.setDefaultConfiguration(config)
    }

}