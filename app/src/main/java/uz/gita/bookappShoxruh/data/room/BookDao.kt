package uz.gita.bookappShoxruh.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertList(bookEntity: List<BookEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(bookEntity: BookEntity)

    @Update
    suspend fun update(bookEntity: BookEntity)

    @Delete
    suspend fun delete(bookEntity: BookEntity)

    @Query("SELECT*FROM books WHERE documentName =(:bookDocument) LIMIT 1")
    fun getBook(bookDocument: String): Flow<BookEntity>

    @Query("SELECT *FROM books WHERE isHistory==1")
    fun getHistoryData(): Flow<List<BookEntity>>
}