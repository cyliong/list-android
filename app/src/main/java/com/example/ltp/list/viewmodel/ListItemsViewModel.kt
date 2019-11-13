package com.example.ltp.list.viewmodel

import com.example.ltp.list.model.ListItem
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where

class ListItemsViewModel {

    private val realm = Realm.getDefaultInstance()

    val listItems: RealmResults<ListItem>

    init {
        listItems = realm.where<ListItem>().findAll()
    }

    fun finalize() {
        realm.close()
    }

    fun addItem(title: String) {
        realm.executeTransaction { realm ->
            realm.insert(ListItem(title = title))
        }
    }

    fun updateItem(index: Int, title: String) {
        realm.executeTransaction { realm ->
            listItems[index]?.run {
                this.title = title
                realm.insertOrUpdate(this)
            }
        }
    }

    fun deleteItem(index: Int) {
        realm.executeTransaction {
            listItems[index]?.deleteFromRealm()
        }
    }

}