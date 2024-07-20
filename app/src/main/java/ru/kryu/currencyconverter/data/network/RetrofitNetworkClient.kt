package ru.kryu.currencyconverter.data.network

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kryu.currencyconverter.R
import ru.kryu.currencyconverter.data.dto.ConvertRequest
import ru.kryu.currencyconverter.util.ConnectionChecker
import javax.inject.Inject

class RetrofitNetworkClient @Inject constructor(
    private val apiService: ApiService,
    private val context: Context
) : NetworkClient {
    override suspend fun doRequest(request: Request): Response {
        if (!ConnectionChecker.isConnected(context)) {
            return Response().apply {
                resultCode = CODE_NO_INTERNET
                text = context.resources.getString(R.string.check_connection)
            }
        }
        return withContext(Dispatchers.IO) {
            when (request) {
                is ConvertRequest -> getConvertInfo(request.to, request.from, request.amount)
                else -> Response().apply {
                    resultCode = CODE_WRONG_REQUEST
                    text = context.resources.getString(R.string.wrong_request)
                }
            }
        }
    }

    private suspend fun getConvertInfo(
        to: String,
        from: String,
        amount: Double
    ): Response {
        return try {
            val response = apiService.getConvertInfo(to, from, amount)
            if (response.code() == CODE_SUCCESS) {
                response.body()?.apply {
                    resultCode = CODE_SUCCESS
                } ?: Response().apply {
                    resultCode = CODE_EXCEPTION
                    text = response.message()
                }
            } else {
                Response().apply {
                    resultCode = response.code()
                    text = response.message()
                }
            }
        } catch (e: Exception) {
            Response().apply {
                resultCode = CODE_EXCEPTION
                text = e.message
            }
        }
    }

    companion object {
        const val CODE_NO_INTERNET = -1
        const val CODE_EXCEPTION = -2
        const val CODE_SUCCESS = 200
        const val CODE_WRONG_REQUEST = 400
    }
}