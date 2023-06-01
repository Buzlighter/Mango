package com.test.mango.data.model

//sealed class Status<out T : Any> {
//    data class Success<out T : Any>(val data: T) : Status<T>()
//    data class Error<out T: Any>(val errorMsg: String) : Status<T>()
//    object Loading : Status<Nothing>()
//}

sealed class Status<T>(val data: T? = null, val errorMsg: String? = null) {
    class Success<T>(data: T?): Status<T>(data)
    class Error<T>(errorMsg: String?): Status<T>(null, errorMsg)
    class Loading<T>: Status<T>()
    class StateLess<T>: Status<T>()
}
