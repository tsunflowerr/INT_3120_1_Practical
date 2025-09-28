package com.example.a30daysofexercise.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Exercise (
    @StringRes val titleRes: Int,
    @StringRes val descriptionRes: Int,
    @DrawableRes val imageRes: Int
)