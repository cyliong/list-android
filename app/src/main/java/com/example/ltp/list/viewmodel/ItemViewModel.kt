package com.example.ltp.list.viewmodel

import com.example.ltp.list.model.ListItem
import io.realm.Realm
import io.realm.kotlin.where

class ItemViewModel(private val id: String? = null) {

    val title: String?
        get() = item?.title

    private val realm = Realm.getDefaultInstance()
    private val item: ListItem?

    init {
        item = id?.let { realm.where<ListItem>().equalTo("id", id).findFirst() }
    }

    fun onDestroy() {
        realm.close()
    }

    fun onSave(title: String) {
        realm.executeTransaction {
            if (item == null) {
                realm.insert(ListItem(title = title))
            } else {
                item.title = title
            }
        }
    }

}