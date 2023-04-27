package com.ulyanenko.shopinglist.di

import android.app.Application
import com.ulyanenko.shopinglist.data.ShopListProvider
import com.ulyanenko.shopinglist.presentation.MainActivity
import com.ulyanenko.shopinglist.presentation.ShopItemFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: ShopItemFragment)

    fun inject(provider:ShopListProvider)

    @Component.Factory
    interface Factory{

        fun create(@BindsInstance application: Application):ApplicationComponent

    }

}