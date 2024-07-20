package ru.kryu.currencyconverter.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.kryu.currencyconverter.data.dto.ConvertInfoDto

interface ApiService {

    @GET("convert")
    suspend fun getConvertInfo(
        @Query("to") to: String,
        @Query("from") from: String,
        @Query("amount") amount: Double,
    ): Response<ConvertInfoDto>
}