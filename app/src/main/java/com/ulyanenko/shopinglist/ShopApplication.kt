package com.ulyanenko.shopinglist

import android.app.Application
import com.ulyanenko.shopinglist.di.DaggerApplicationComponent

class ShopApplication : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}