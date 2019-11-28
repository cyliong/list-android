package com.example.ltp.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.ltp.list.viewmodel.ItemViewModel
import kotlinx.android.synthetic.main.activity_item.*
import kotlinx.android.synthetic.main.content_item.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class ItemActivity : AppCompatActivity() {

    private lateinit var viewModel: ItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        edit_text_item_title.requestFocus()

        val itemId = intent.getStringExtra(EXTRA_ITEM_ID)
        viewModel = ItemViewModel(itemId)
        if (itemId == null) {
            title = "New Item"
        } else {
            edit_text_item_title.append(viewModel.title)
        }
    }

    override fun onDestroy() {
        viewModel.onDestroy()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_save -> {
            val title = edit_text_item_title.text?.toString()
            if (title.isNullOrBlank()) {
                alert("Please enter an item.") { yesButton {} }.show()
                false
            } else {
                viewModel.onSave(title)
                finish()
                true
            }
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val EXTRA_ITEM_ID = "item_id"
    }

}
