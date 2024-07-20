package ru.kryu.currencyconverter.data.dto


import com.google.gson.annotations.SerializedName

data class ConvertInfoDto(
    val date: String,
    val historical: Boolean,
    val info: Info,
    val query: Query,
    val result: Double,
    val success: Boolean
) {
    data class Info(
        val rate: Double,
        val timestamp: Int
    )

    data class Query(
        val amount: Int,
        val from: String,
        val to: String
    )
}