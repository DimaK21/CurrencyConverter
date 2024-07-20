package ru.kryu.currencyconverter.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kryu.currencyconverter.data.ConvertRepositoryImpl
import ru.kryu.currencyconverter.data.network.NetworkClient
import ru.kryu.currencyconverter.domain.ConvertRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideConvertRepository(networkClient: NetworkClient): ConvertRepository =
        ConvertRepositoryImpl(networkClient)
}