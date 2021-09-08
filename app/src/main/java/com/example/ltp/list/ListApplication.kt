package com.example.ltp.list

import androidx.multidex.MultiDexApplication
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.realm.Realm
import io.realm.RealmConfiguration

const val ENGINE_ID = "list.engine"

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

        val flutterEngine = FlutterEngine(this)
        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )
        FlutterEngineCache.getInstance().put(ENGINE_ID, flutterEngine)
    }

}