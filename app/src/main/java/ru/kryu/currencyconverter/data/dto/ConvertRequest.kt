package ru.kryu.currencyconverter.data.dto

import ru.kryu.currencyconverter.data.network.Request

data class ConvertRequest(
    val to: String,
    val from: String,
    val amount: Double,
) : Request
