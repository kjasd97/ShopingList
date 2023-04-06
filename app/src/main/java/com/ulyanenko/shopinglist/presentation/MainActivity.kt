package com.ulyanenko.shopinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Adapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
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
            adapter.shopList = it
        }


    }

    private fun setupRecycleView() {
        adapter = ShopListAdapter()
        val recycleView = findViewById<RecyclerView>(R.id.rv_shop_list)
        recycleView.adapter = adapter
        recycleView.recycledViewPool.setMaxRecycledViews(R.layout.item_shop_enabled, ShopListAdapter.MAX_POOL)
        recycleView.recycledViewPool.setMaxRecycledViews(R.layout.item_shop_disabled, ShopListAdapter.MAX_POOL)

    }


}