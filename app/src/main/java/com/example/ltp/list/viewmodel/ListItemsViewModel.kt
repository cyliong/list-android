package com.example.ltp.list.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ltp.list.model.ListItem
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where

class ListItemsViewModel : ViewModel() {

    val listItems: RealmResults<ListItem>

    private val realm = Realm.getDefaultInstance()

    init {
        listItems = realm.where<ListItem>().sort("created").findAll()
    }

    fun onDestroy() {
        realm.close()
    }

    fun onDelete(index: Int) {
        listItems[index]?.id?.let { id ->
            realm.executeTransactionAsync { backgroundRealm ->
                backgroundRealm
                    .where<ListItem>()
                    .equalTo("id", id)
                    .findFirst()
                    ?.deleteFromRealm()
            }
        }
    }

}