package com.ulyanenko.shopinglist.di

import android.app.Application
import com.ulyanenko.shopinglist.data.AppDatabase
import com.ulyanenko.shopinglist.data.ShopListDAO
import com.ulyanenko.shopinglist.data.ShopListRepositoryImpl
import com.ulyanenko.shopinglist.domain.ShopListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindShopListRepository(impl: ShopListRepositoryImpl):ShopListRepository

    companion object{

        @ApplicationScope
        @Provides
        fun providesShopListDao(application: Application):ShopListDAO{
            return AppDatabase.getInstance(application).shopListDao()
        }

    }

}