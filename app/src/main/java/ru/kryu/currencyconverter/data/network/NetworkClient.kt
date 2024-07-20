package ru.kryu.currencyconverter.data.network

interface NetworkClient {
    suspend fun doRequest(request: Request): Response
}