package com.example.ltp.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ltp.list.databinding.ActivityListItemsBinding
import com.example.ltp.list.databinding.RowListItemBinding
import com.example.ltp.list.model.ListItem
import com.example.ltp.list.viewmodel.ListItemsViewModel
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter

class ListItemsActivity : AppCompatActivity() {

    private val viewModel = ListItemsViewModel()

    private lateinit var binding: ActivityListItemsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val recyclerView = binding.contentListItems.recyclerView
        val linearLayoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            linearLayoutManager.orientation
        )

        recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = ListItemsAdapter(viewModel.listItems)
        }.addItemDecoration(dividerItemDecoration)

        setSwipeToDeleteHandler()

        binding.fab.setOnClickListener {
            startActivity(ItemActivity.newIntent(this))
        }
    }

    override fun onDestroy() {
        viewModel.onDestroy()
        super.onDestroy()
    }

    private inner class ListItemsAdapter(private val items: OrderedRealmCollection<ListItem>) :
        RealmRecyclerViewAdapter<ListItem, ListItemsAdapter.ViewHolder>(items, true) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemBinding = RowListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ViewHolder(itemBinding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(items[position])
        }

        inner class ViewHolder(private val itemBinding: RowListItemBinding) :
            RecyclerView.ViewHolder(itemBinding.root) {

            init {
                itemBinding.root.setOnClickListener {
                    startActivity(
                        ItemActivity.newIntent(
                            this@ListItemsActivity,
                            items[adapterPosition].id
                        )
                    )
                }
            }

            fun bind(item: ListItem) {
                itemBinding.textViewListItem.text = item.title
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

        ItemTouchHelper(swipeToDeleteHandler)
            .attachToRecyclerView(binding.contentListItems.recyclerView)
    }

}
