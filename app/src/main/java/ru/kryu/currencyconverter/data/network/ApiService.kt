package ru.kryu.currencyconverter.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import ru.kryu.currencyconverter.data.dto.ConvertInfoDto

// никому не говори, что он здесь ;-)
const val API_KEY = "ZzjlwySXaUCQvypP16gPwHNGzcLYoM78"

interface ApiService {

    @Headers("apikey: $API_KEY")
    @GET("convert")
    suspend fun getConvertInfo(
        @Query("to") to: String,
        @Query("from") from: String,
        @Query("amount") amount: Double,
    ): Response<ConvertInfoDto>
}
