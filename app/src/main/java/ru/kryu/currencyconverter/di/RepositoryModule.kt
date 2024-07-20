package ru.kryu.currencyconverter.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kryu.currencyconverter.data.ConvertRepositoryImpl
import ru.kryu.currencyconverter.data.network.NetworkClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideConvertRepository(networkClient: NetworkClient): ConvertRepositoryImpl =
        ConvertRepositoryImpl(networkClient)
}