package com.example.jetpackcompose.di

import android.app.Application
import com.example.jetpackcompose.data.DataSource.RemoteDataSource
import com.example.jetpackcompose.data.DataSource.RemoteDataSourceImp
import com.example.jetpackcompose.data.Repository.Repository
import com.example.jetpackcompose.data.Repository.RepositoryImp
import com.example.jetpackcompose.view.MainViewModel
import com.example.jetpackcompose.network.ApiService
import com.example.jetpackcompose.network.ApiServiceProvider
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
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

        val myModules = module {
            single { ApiServiceProvider.getApiService() }
            factory<RemoteDataSource> { RemoteDataSourceImp(get()) }
            factory<Repository> { RepositoryImp(get()) }
            viewModel { MainViewModel(get()) }
        }



        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(listOf(myModules))
        }
    }
}