package com.example.ltp.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ltp.list.model.ListItem
import io.realm.Realm
import io.realm.kotlin.where

class ItemViewModel(private val id: String? = null) : ViewModel() {

    val title: String?
        get() = item?.title

    private val realm = Realm.getDefaultInstance()
    private val item: ListItem?

    init {
        item = getItem(realm)
    }

    override fun onCleared() {
        realm.close()
        super.onCleared()
    }

    fun onSave(title: String) {
        realm.executeTransactionAsync {
            val item = getItem(it)
            if (item == null) {
                it.insert(ListItem(title = title))
            } else {
                item.title = title
            }
        }
    }

    private fun getItem(realm: Realm): ListItem? {
        return id?.let { realm.where<ListItem>().equalTo("id", id).findFirst() }
    }

}

class ItemViewModelFactory (private val id: String?) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ItemViewModel(id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}