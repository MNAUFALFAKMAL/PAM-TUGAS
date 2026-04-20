package com.example.myfirstkmpapp.ui

sealed interface ResultState<out T> {
    object Loading : ResultState<Nothing>
    data class Success<T>(val data: T) : ResultState<T>
    data class Error(val errorMessage: String) : ResultState<Nothing>
}