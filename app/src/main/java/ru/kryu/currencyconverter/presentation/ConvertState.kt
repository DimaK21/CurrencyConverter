package ru.kryu.currencyconverter.presentation

sealed interface ConvertState {
    data object Loading : ConvertState
    data class Error(val message: String)  : ConvertState
    data class Content(val result: Double) : ConvertState
}