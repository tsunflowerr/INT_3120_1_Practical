package com.example.bookshelf.data

import com.example.bookshelf.model.Book
import com.example.bookshelf.network.BookshelfApiService
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class NetworkBookshelfRepository(
    private val bookshelfApiService: BookshelfApiService
) : BookshelfRepository {

    override suspend fun getBooks(query: String): List<Book> = coroutineScope {
        val searchResponse = bookshelfApiService.searchBooks(query)
        val bookItems = searchResponse.items ?: return@coroutineScope emptyList()

        // Fetch detailed info for each book in parallel
        val books = bookItems.map { item ->
            async {
                try {
                    val detailedBook = bookshelfApiService.getBook(item.id)
                    Book(
                        id = detailedBook.id,
                        title = detailedBook.volumeInfo.title ?: "Unknown Title",
                        authors = detailedBook.volumeInfo.authors?.joinToString(", ") ?: "Unknown Author",
                        imageUrl = detailedBook.volumeInfo.imageLinks?.thumbnail?.replace("http:", "https:") ?: ""
                    )
                } catch (e: Exception) {
                    null
                }
            }
        }.awaitAll()

        books.filterNotNull()
    }
}
