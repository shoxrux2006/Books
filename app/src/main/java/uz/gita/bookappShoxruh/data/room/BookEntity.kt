package uz.gita.bookappShoxruh.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var isLike: Int,
    var filePath: String,
    var isHistory: Int,
    val documentName: String,
)
