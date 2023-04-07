package com.ulyanenko.shopinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Adapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ulyanenko.shopinglist.R
import com.ulyanenko.shopinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    lateinit var adapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecycleView()

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.shopList.observe(this) {
            adapter.submitList(it)
        }

        val buttonAddItem = findViewById<FloatingActionButton>(R.id.button_add_shop_item)
        buttonAddItem.setOnClickListener {
            val intent = ShopItemActivity.newIntentAdd(this)
            startActivity(intent)
        }


    }

    private fun setupRecycleView() {
        adapter = ShopListAdapter()
        val recycleView = findViewById<RecyclerView>(R.id.rv_shop_list)
        recycleView.adapter = adapter
        recycleView.recycledViewPool.setMaxRecycledViews(
            R.layout.item_shop_enabled,
            ShopListAdapter.MAX_POOL
        )
        recycleView.recycledViewPool.setMaxRecycledViews(
            R.layout.item_shop_disabled,
            ShopListAdapter.MAX_POOL
        )

        setupOnLongClickListener()

        setupClickListener()

        setupSwipeListener(recycleView)

    }

    private fun setupSwipeListener(recycleView: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item)
            }

        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recycleView)
    }


    private fun setupClickListener() {
        adapter.onShopItemClickListener = {
            val intent = ShopItemActivity.newIntentEdit(this, it.id)
            startActivity(intent)
        }
    }

    private fun setupOnLongClickListener() {
        adapter.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }


}