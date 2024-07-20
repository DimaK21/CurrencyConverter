package ru.kryu.currencyconverter.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.kryu.currencyconverter.data.dto.ConvertInfoDto
import ru.kryu.currencyconverter.data.dto.ConvertRequest
import ru.kryu.currencyconverter.data.network.NetworkClient
import ru.kryu.currencyconverter.data.network.RetrofitNetworkClient
import ru.kryu.currencyconverter.domain.ConvertRepository
import ru.kryu.currencyconverter.util.Resource
import javax.inject.Inject

class ConvertRepositoryImpl @Inject constructor(
    private val networkClient: NetworkClient
) : ConvertRepository {
    override suspend fun getConvertInfo(
        to: String,
        from: String,
        amount: Double
    ): Flow<Resource<Double>> = flow {
        val response = networkClient.doRequest(ConvertRequest(to, from, amount))
        emit(
            when (response.resultCode) {
                RetrofitNetworkClient.CODE_SUCCESS -> {
                    Resource.Success((response as ConvertInfoDto).result)
                }

                else -> {
                    Resource.Error("${response.resultCode} ${response.text}")
                }
            }
        )
    }
}