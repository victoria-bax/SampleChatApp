package com.vicabax.samplechatapp.data.model

import androidx.annotation.DrawableRes

data class User(
    val id: String,
    val name: String,
    @DrawableRes
    val image: Int?,
)
