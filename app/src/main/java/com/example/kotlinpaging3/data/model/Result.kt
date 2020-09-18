package com.example.kotlinpaging3.data.model

import java.lang.Exception

/**
 * Result management for UI and data.
 */
sealed class Result<out T> {

    data class Success<T>(val data: T) : Result<T>()

    data class Error(val exception: Exception) : Result<Nothing>()

    companion object {

        /**
         * Return [Success] with [data] to emit.
         */
        fun <T> success(data: T) = Success(data)

        /**
         * Return [Error] with [exception] to emit.
         */
        fun error(exception: Exception) = Error(exception)
    }
}
