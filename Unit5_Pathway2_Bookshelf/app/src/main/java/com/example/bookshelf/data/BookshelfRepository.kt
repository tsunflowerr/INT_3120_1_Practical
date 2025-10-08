package com.example.bookshelf.data

import com.example.bookshelf.model.Book

interface BookshelfRepository {
    suspend fun getBooks(query: String): List<Book>
}