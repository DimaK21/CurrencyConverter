package ru.kryu.currencyconverter.data.dto

import ru.kryu.currencyconverter.data.network.Response

data class ConvertInfoDto(
    val date: String,
    val historical: Boolean,
    val info: Info,
    val query: Query,
    val result: Double,
    val success: Boolean
) : Response() {
    data class Info(
        val rate: Double,
        val timestamp: Int
    )

    data class Query(
        val amount: Double,
        val from: String,
        val to: String
    )
}