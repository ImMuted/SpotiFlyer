package com.shabinder.common.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.shabinder.common.di.Dir
import com.shabinder.common.models.DownloadRecord
import com.shabinder.common.di.Picture
import com.shabinder.common.main.integration.SpotiFlyerMainImpl
import com.shabinder.common.utils.Consumer
import com.shabinder.database.Database
import kotlinx.coroutines.flow.Flow

interface SpotiFlyerMain {

    val models: Flow<State>

    /*
    * We Intend to Move to List Screen
    * Note: Implementation in Root
    * */
    fun onLinkSearch(link: String)

    /*
    * Update TextBox's Text
    * */
    fun onInputLinkChanged(link: String)

    /*
    * change TabBar Selected Category
    * */
    fun selectCategory(category: HomeCategory)

    /*
    * Load Image from cache/Internet and cache it
    * */
    fun loadImage(url:String): Picture?

    interface Dependencies {
        fun mainOutput(searched: Output): Consumer<Output>
        val storeFactory: StoreFactory
        val database: Database
        val dir: Dir
    }
    sealed class Output {
        data class Search(val link: String) : Output()
    }

    data class State(
        val records: List<DownloadRecord> = emptyList(),
        val link: String = "",
        val selectedCategory: HomeCategory = HomeCategory.About
    )
    enum class HomeCategory {
        About, History
    }
}

@Suppress("FunctionName") // Factory function
fun SpotiFlyerMain(componentContext: ComponentContext, dependencies: SpotiFlyerMain.Dependencies): SpotiFlyerMain =
    SpotiFlyerMainImpl(componentContext, dependencies)