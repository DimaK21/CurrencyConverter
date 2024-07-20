package ru.kryu.currencyconverter.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.kryu.currencyconverter.data.network.ApiService
import ru.kryu.currencyconverter.data.network.NetworkClient
import ru.kryu.currencyconverter.data.network.RetrofitNetworkClient
import javax.inject.Singleton

const val BASE_URL = "https://api.apilayer.com/exchangerates_data"

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideNetworkClient(
        apiService: ApiService,
        @ApplicationContext context: Context,
    ): NetworkClient = RetrofitNetworkClient(
        apiService = apiService,
        context = context
    )

    @Provides
    @Singleton
    fun provideApiService(): ApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
}