package br.com.tosin.newconceptsandroid.utils

import br.com.tosin.newconceptsandroid.R
import retrofit2.HttpException

object DefineMessageError {
    fun getMessage(exception: HttpException): Int {
        var message = 0

        val code = exception.code()

        // 1xx Informational
        when (code) {
            in 100..199 -> message = R.string.request_default_1xx
            // 2xx Success
            in 200..299 -> message = R.string.request_default_2xx
            // 3xx Redirection
            in 300..399 -> message = R.string.request_default_3xx
            // 4xx Client Error
            in 400..499 -> {
                message = R.string.request_default_4xx
//                val errorBody = exception.response().errorBody()
//                val errorString = errorBody?.string()
            }
            // 5xx Server Error
            in 500..599 -> message = R.string.request_default_5xx
        }

        return message
    }
}