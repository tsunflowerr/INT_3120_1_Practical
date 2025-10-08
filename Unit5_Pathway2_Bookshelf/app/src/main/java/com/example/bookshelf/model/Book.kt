package com.example.bookshelf.model

import kotlinx.serialization.Serializable

@Serializable
data class BookSearchResponse(
    val items: List<BookItem>? = null
)

@Serializable
data class BookItem(
    val id: String,
    val volumeInfo: VolumeInfo
)

@Serializable
data class VolumeInfo(
    val title: String? = null,
    val authors: List<String>? = null,
    val imageLinks: ImageLinks? = null
)

@Serializable
data class ImageLinks(
    val thumbnail: String? = null,
    val smallThumbnail: String? = null
)

data class Book(
    val id: String,
    val title: String,
    val authors: String,
    val imageUrl: String
)