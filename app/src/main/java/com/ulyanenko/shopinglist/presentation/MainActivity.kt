package com.ulyanenko.shopinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Adapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ulyanenko.shopinglist.R
import com.ulyanenko.shopinglist.ShopApplication
import com.ulyanenko.shopinglist.databinding.ActivityMainBinding
import com.ulyanenko.shopinglist.di.DaggerApplicationComponent
import com.ulyanenko.shopinglist.domain.ShopItem
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    private lateinit var viewModel: MainViewModel
    lateinit var adapter: ShopListAdapter
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

   private val component by lazy {
       (application as ShopApplication).component
   }

    override fun onCreate(savedInstanceState: Bundle?) {
       component.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        setupRecycleView()

        viewModel.shopList.observe(this) {
            adapter.submitList(it)
        }

        binding.buttonAddShopItem.setOnClickListener {
            if (isOnePainMode()) {
                val intent = ShopItemActivity.newIntentAdd(this)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newFragmentAdd())
            }
        }


    }

    private fun isOnePainMode(): Boolean {
        return binding.shopItemContainer == null
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupRecycleView() {
        adapter = ShopListAdapter()

        binding.rvShopList.adapter = adapter
        binding.rvShopList.recycledViewPool.setMaxRecycledViews(
            R.layout.item_shop_enabled,
            ShopListAdapter.MAX_POOL
        )
        binding.rvShopList.recycledViewPool.setMaxRecycledViews(
            R.layout.item_shop_disabled,
            ShopListAdapter.MAX_POOL
        )

        setupOnLongClickListener()

        setupClickListener()

        setupSwipeListener(binding.rvShopList)

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
            if (isOnePainMode()) {
                val intent = ShopItemActivity.newIntentEdit(this, it.id)
                startActivity(intent)
            } else {
                supportFragmentManager.beginTransaction()
                    .add(R.id.shop_item_container, ShopItemFragment.newFragmentEdit(it.id))
            }
        }
    }

    private fun setupOnLongClickListener() {
        adapter.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }

    override fun onEditingListener() {
        Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }


}