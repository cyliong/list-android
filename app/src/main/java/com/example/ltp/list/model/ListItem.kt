package com.example.ltp.list.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class ListItem(

    @PrimaryKey var id: String = UUID.randomUUID().toString(),
    var title: String = "",
    var note: String? = null,
    var created: Date = Date()

) : RealmObject()