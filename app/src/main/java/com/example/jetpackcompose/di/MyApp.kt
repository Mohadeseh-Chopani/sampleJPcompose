package com.example.jetpackcompose.di

import android.app.Application
import com.example.jetpackcompose.data.DataSource.RemoteDataSourceImp
import com.example.jetpackcompose.data.Repository.RepositoryImp
import com.example.jetpackcompose.view.MainViewModel
import com.example.jetpackcompose.network.ApiService
import com.example.jetpackcompose.network.ApiServiceProvider
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()

        val myModule = module {
            singleOf(ApiServiceProvider::getApiService)
            factoryOf(::RemoteDataSourceImp)
            factoryOf(::RepositoryImp)
            viewModelOf(::MainViewModel)
        }


        startKoin {
            androidContext(this@MyApp)
            modules(listOf(myModule))
        }
    }
}