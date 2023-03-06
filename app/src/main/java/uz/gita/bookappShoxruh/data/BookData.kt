package uz.gita.bookappShoxruh.data

import android.os.Parcelable
import com.google.firebase.firestore.DocumentSnapshot
import uz.gita.bookappShoxruh.data.room.BookEntity
import uz.gita.bookappShoxruh.utils.Const
import java.io.Serializable

data class BookData(
    val author: String,
    val bookName: String,
    val description: String,
    val downloadLink: String,
    var downloads: Long,
    val genre: String,
    val imageLink: String,
    var like: Long,
    val pageSize: Long,
    val realizedDate: Long,
    val isTrend: Boolean,
    val size: Long,
    val documentName: String,
) : Serializable

fun BookData.toBookEntity(): BookEntity {
    return BookEntity(0, 0, "", 0,  this.documentName)
}

fun DocumentSnapshot.toBookData(): BookData {
    val date = BookData(
        this.getString(Const.author) ?: "",
        this.getString(Const.bookName) ?: "",
        this.getString(Const.description) ?: "",
        this.getString(Const.downloadLink) ?: "",
        this.getLong(Const.downloads) ?: 0,
        this.getString(Const.genre) ?: "",
        this.getString(Const.imageLink) ?: "",
        this.getLong(Const.like) ?: 0,
        this.getLong(Const.pageSize) ?: 0,
        this.getLong(Const.realizedDate) ?: 0,
        this.getBoolean(Const.isTrend) ?: false,
        this.getLong(Const.size) ?: 0,
        this.id
    )
    return date
}