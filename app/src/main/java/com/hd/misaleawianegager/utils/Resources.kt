package com.hd.misaleawianegager.utils

sealed class Resources<T>(val data: T?  = null, message : String? = null) {
    class Success<T>(data : T ) : Resources<T>(data)
    class Error<T>(message: String?) : Resources<T>(message = message)
    class Loading<T>(isLoading: Boolean ) : Resources<T>(null)
}