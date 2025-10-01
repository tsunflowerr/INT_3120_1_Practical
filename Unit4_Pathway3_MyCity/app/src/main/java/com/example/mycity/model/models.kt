package com.example.mycity.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes


data class Category(
    val id: Int,
    @StringRes val titleResourceId: Int,
    @StringRes val subtitleResourceId: Int,
    @DrawableRes val imageResourceId: Int
)


data class Place(
    val id: Int,
    val categoryId: Int,
    @StringRes val titleResourceId: Int,
    @StringRes val shortDescriptionResourceId: Int,
    @StringRes val detailsResourceId: Int,
    @DrawableRes val imageResourceId: Int,
    @DrawableRes val bannerImageResourceId: Int
)
