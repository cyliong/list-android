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
import com.example.ltp.list.viewmodel.ListItemsViewModel
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_list_items.*
import kotlinx.android.synthetic.main.content_list_items.*
import kotlinx.android.synthetic.main.row_list_item.view.*
import org.jetbrains.anko.*

class ListItemsActivity : AppCompatActivity() {

    private val viewModel = ListItemsViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_items)
        setSupportActionBar(toolbar)

        val linearLayoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(recycler_view.context, linearLayoutManager.orientation)

        recycler_view.apply {
            layoutManager = linearLayoutManager
            adapter = ListItemsAdapter(viewModel.listItems)
        }.addItemDecoration(dividerItemDecoration)

        setSwipeToDeleteHandler()

        fab.setOnClickListener { startActivity<ItemActivity>() }
    }

    override fun onDestroy() {
        viewModel.onDestroy()
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
                view.setOnClickListener {
                    startActivity<ItemActivity>(
                        ItemActivity.EXTRA_ITEM_ID to items[adapterPosition].id
                    )
                }
            }

        }

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
                viewModel.onDelete(viewHolder.adapterPosition)
            }

        }

        ItemTouchHelper(swipeToDeleteHandler).attachToRecyclerView(recycler_view)
    }

}
