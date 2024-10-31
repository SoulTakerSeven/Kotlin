package com.example.core.http

import androidx.annotation.NonNull

interface EntityCallback<T> {

    fun onSuccess(@NonNull entity: T)

    fun onFailure(message: String?)
}