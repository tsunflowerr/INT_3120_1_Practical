package com.example.bookshelf.network

import com.example.bookshelf.model.BookItem
import com.example.bookshelf.model.BookSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookshelfApiService {
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String
    ): BookSearchResponse

    @GET("volumes/{id}")
    suspend fun getBook(
        @Path("id") id: String
    ): BookItem
}