package com.example.ltp.list.db

import io.realm.DynamicRealm
import io.realm.RealmMigration

class Migration : RealmMigration {
    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        var oldVersion = oldVersion

        val schema = realm.schema

        if (oldVersion == 0L) {
            schema.get("ListItem")!!
                .addField("note", String::class.java)
            oldVersion++
        }
    }
}