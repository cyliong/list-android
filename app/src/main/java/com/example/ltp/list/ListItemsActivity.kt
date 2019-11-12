package com.example.ltp.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ltp.list.model.ListItem
import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.RealmRecyclerViewAdapter
import io.realm.RealmResults
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_list_items.*
import kotlinx.android.synthetic.main.content_list_items.*
import kotlinx.android.synthetic.main.row_list_item.view.*
import org.jetbrains.anko.*

class ListItemsActivity : AppCompatActivity() {

    private lateinit var realm: Realm
    private lateinit var listItems: RealmResults<ListItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_items)
        setSupportActionBar(toolbar)

        realm = Realm.getDefaultInstance()
        listItems = realm.where<ListItem>().findAll()

        val linearLayoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(recycler_view.context, linearLayoutManager.orientation)

        recycler_view.apply {
            layoutManager = linearLayoutManager
            adapter = ListItemsAdapter(listItems)
        }.addItemDecoration(dividerItemDecoration)

        setSwipeToDeleteHandler()

        fab.setOnClickListener { showInputDialog() }
    }

    override fun onDestroy() {
        realm.close()
        super.onDestroy()
    }

    private inner class ListItemsAdapter(private val items: OrderedRealmCollection<ListItem>) :
        RealmRecyclerViewAdapter<ListItem, ListItemsAdapter.ViewHolder>(items, true) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.row_list_item, parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.view.text_view_list_item.text = items[position]?.title
        }

        inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

            init {
                view.setOnClickListener { showInputDialog(adapterPosition) }
            }

        }

    }

    private fun showInputDialog(position: Int = -1) {
        val isNew = position == -1

        alert {
            title = if (isNew) "New Item" else "Edit Item"
            customView {
                verticalLayout {
                    padding = dip(20)

                    val editText = editText {
                        if (isNew) {
                            hint = "Enter a new item"
                        } else {
                            listItems[position]?.run {
                                append(title)
                            }
                        }
                    }

                    positiveButton("Save") {
                        val itemTitle = editText.text.toString()

                        if (itemTitle.isEmpty()) {
                            showInputDialog(position)
                            return@positiveButton
                        }

                        realm.executeTransaction { realm ->
                            if (isNew) {
                                val listItem = ListItem(title = itemTitle)
                                realm.insert(listItem)
                            } else {
                                listItems[position]?.run {
                                    title = itemTitle
                                    realm.insertOrUpdate(this)
                                }
                            }
                        }
                    }
                    negativeButton("Cancel") {}
                }
            }
        }.show()
    }

    private fun setSwipeToDeleteHandler() {
        val swipeToDeleteHandler = object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                realm.executeTransaction {
                    listItems[viewHolder.adapterPosition]?.deleteFromRealm()
                }
            }

        }

        ItemTouchHelper(swipeToDeleteHandler).attachToRecyclerView(recycler_view)
    }

}
