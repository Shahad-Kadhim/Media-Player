package com.example.mediaplayer.util

sealed class Status<out T>{
    object Loading:Status<Nothing>()
    object Error : Status<Nothing>()
    data class Success<T>(val data: T): Status<T>()
}
