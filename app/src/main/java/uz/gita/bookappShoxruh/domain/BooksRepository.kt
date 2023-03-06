package uz.gita.bookappShoxruh.domain

import kotlinx.coroutines.flow.Flow
import uz.gita.banking.utils.NetworkResponse
import uz.gita.bookappShoxruh.data.BookData
import uz.gita.bookappShoxruh.data.UploadData
import uz.gita.bookappShoxruh.data.room.BookEntity

interface BooksRepository {
    suspend fun getBook(document: String): Flow<BookEntity>
    suspend fun deleteBook(bookEntity: BookEntity)
    suspend fun insertBook(bookEntity: BookEntity)
    suspend fun insertBooks(bookEntity: List<BookEntity>)
    suspend fun updateBook(bookEntity: BookEntity)
    fun getAllBooks(): Flow<NetworkResponse<List<BookData>>>
    suspend fun getHistory(): List<BookData>
    fun like(bookData: BookData): Flow<NetworkResponse<Long>>
    fun dislike(bookData: BookData): Flow<NetworkResponse<Long>>
    fun downloadBook(link: String): Flow<UploadData>
    fun pauseDownload()
    fun resumeDownload()
    fun cancelDownload()
}