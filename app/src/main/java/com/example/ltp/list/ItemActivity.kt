package com.example.ltp.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.ltp.list.viewmodel.ItemViewModel
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_item.*
import kotlinx.android.synthetic.main.content_item.*
import java.util.concurrent.TimeUnit

private const val EXTRA_ITEM_ID = "com.example.ltp.list.EXTRA_ITEM_ID"

class ItemActivity : AppCompatActivity() {

    private val disposables = CompositeDisposable()

    private lateinit var viewModel: ItemViewModel
    private var saveItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val itemId = intent.getStringExtra(EXTRA_ITEM_ID)
        viewModel = ItemViewModel(itemId)
        if (itemId == null) {
            title = "New Item"
        } else {
            edit_text_item_title.append(viewModel.title)
        }

        edit_text_item_title.run {
            requestFocus()

            val disposable = textChanges()
                .throttleLatest(100, TimeUnit.MILLISECONDS, true)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { charSequence ->
                    saveItem?.isEnabled = !charSequence.isNullOrBlank()
                }
            disposables.add(disposable)
        }
    }

    override fun onDestroy() {
        disposables.clear()
        viewModel.onDestroy()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_item, menu)
        saveItem = menu.findItem(R.id.action_save)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_save -> {
            viewModel.onSave(edit_text_item_title.text.toString())
            finish()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    companion object {
        fun newIntent(context: Context, itemId: String? = null): Intent {
            return Intent(context, ItemActivity::class.java).apply {
                itemId?.let { putExtra(EXTRA_ITEM_ID, it) }
            }
        }
    }

}
