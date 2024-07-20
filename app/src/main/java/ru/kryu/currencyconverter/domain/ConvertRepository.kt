package ru.kryu.currencyconverter.domain

import kotlinx.coroutines.flow.Flow
import ru.kryu.currencyconverter.util.Resource

interface ConvertRepository {
    suspend fun getConvertInfo(to: String, from: String, amount: Double): Flow<Resource<Double>>
}