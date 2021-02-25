package com.example.ltp.list

import androidx.multidex.MultiDexApplication
import io.realm.Realm
import io.realm.RealmConfiguration

class ListApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .schemaVersion(1)
            .migration { realm, oldVersion, _ ->
                var oldVersion = oldVersion

                val schema = realm.schema

                if (oldVersion == 0L) {
                    schema.get("ListItem")!!
                        .addField("note", String::class.java)
                    oldVersion++
                }
            }
            .build()
        Realm.setDefaultConfiguration(config)
    }

}