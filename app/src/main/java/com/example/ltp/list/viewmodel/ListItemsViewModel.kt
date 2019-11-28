package com.example.ltp.list.viewmodel

import com.example.ltp.list.model.ListItem
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where

class ListItemsViewModel {

    val listItems: RealmResults<ListItem>

    private val realm = Realm.getDefaultInstance()

    init {
        listItems = realm.where<ListItem>().findAll()
    }

    fun onDestroy() {
        realm.close()
    }

    fun onDelete(index: Int) {
        realm.executeTransaction {
            listItems[index]?.deleteFromRealm()
        }
    }

}