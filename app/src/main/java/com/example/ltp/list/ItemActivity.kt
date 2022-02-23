package com.example.ltp.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.ltp.list.databinding.ActivityItemBinding
import com.example.ltp.list.viewmodel.ItemViewModel
import com.example.ltp.list.viewmodel.ItemViewModelFactory
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

private const val EXTRA_ITEM_ID = "com.example.ltp.list.EXTRA_ITEM_ID"

class ItemActivity : AppCompatActivity() {

    private val disposables = CompositeDisposable()

    private lateinit var viewModel: ItemViewModel
    private lateinit var binding: ActivityItemBinding

    private var saveItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val editTextItemTitle = binding.contentItem.editTextItemTitle
        val itemId = intent.getStringExtra(EXTRA_ITEM_ID)

        val viewModelFactory = ItemViewModelFactory(itemId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ItemViewModel::class.java)

        if (itemId == null) {
            title = "New Item"
        } else {
            editTextItemTitle.append(viewModel.title)
        }

        editTextItemTitle.run {
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
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_item, menu)
        saveItem = menu.findItem(R.id.action_save)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_save -> {
            viewModel.onSave(binding.contentItem.editTextItemTitle.text.toString())
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
