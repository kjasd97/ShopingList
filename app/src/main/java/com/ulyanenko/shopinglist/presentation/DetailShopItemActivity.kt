package com.ulyanenko.shopinglist.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ulyanenko.shopinglist.R

class DetailShopItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_shop_item)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, DetailShopItemActivity::class.java)
        }
    }
}