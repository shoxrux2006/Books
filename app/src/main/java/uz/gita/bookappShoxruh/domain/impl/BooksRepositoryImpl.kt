package uz.gita.bookappShoxruh.domain.impl

import android.content.Context
import android.os.Environment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageTask
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import uz.gita.banking.utils.NetworkResponse
import uz.gita.banking.utils.hasConnection
import uz.gita.banking.utils.l
import uz.gita.bookappShoxruh.data.BookData
import uz.gita.bookappShoxruh.data.UploadData
import uz.gita.bookappShoxruh.data.room.BookDao
import uz.gita.bookappShoxruh.data.room.BookEntity
import uz.gita.bookappShoxruh.data.toBookData
import uz.gita.bookappShoxruh.domain.BooksRepository
import uz.gita.bookappShoxruh.utils.Const
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context,
    val bookDao: BookDao
) : BooksRepository {
    private lateinit var downTask: StorageTask<FileDownloadTask.TaskSnapshot>
    val baseRoot: FirebaseFirestore = FirebaseFirestore.getInstance()

    override suspend fun getBook(document: String): Flow<BookEntity> {
        return bookDao.getBook(document)
    }

    override suspend fun deleteBook(bookEntity: BookEntity) {
        bookDao.delete(bookEntity)
    }

    override suspend fun insertBook(bookEntity: BookEntity) {
        bookDao.insert(bookEntity)
    }

    override suspend fun insertBooks(bookEntity: List<BookEntity>) {
        bookDao.insertList(bookEntity)
    }

    override suspend fun updateBook(bookEntity: BookEntity) {
        bookDao.update(bookEntity)
    }


    override fun getAllBooks(): Flow<NetworkResponse<List<BookData>>> =
        callbackFlow<NetworkResponse<List<BookData>>> {
            trySend(NetworkResponse.Loading(true))
            baseRoot.collection(Const.books).get()
                .addOnSuccessListener { its ->
                    trySend(NetworkResponse.Loading(false))
                    val data = its.documents.map { it.toBookData() }
                    trySend(NetworkResponse.Success(data)).isSuccess
                }.addOnFailureListener {
                    trySend(NetworkResponse.Loading(false))
                    trySend(NetworkResponse.Error(it.toString()))
                }
            awaitClose {
                channel.close()
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun getHistory(): List<BookData> {
        return emptyList()
    }

    override fun like(bookData: BookData): Flow<NetworkResponse<Long>> =
        callbackFlow<NetworkResponse<Long>> {
            val map: MutableMap<String, Any> = HashMap()
            val base = baseRoot.collection(Const.books).document(bookData.documentName)
            if (hasConnection(context)) {
                base.get()
                    .addOnSuccessListener {
                        map[Const.like] = (it.getLong(Const.like)!! + 1) as Any
                        base.update(map).addOnSuccessListener {
                            trySend(NetworkResponse.Success(map[Const.like] as Long))
                        }.addOnFailureListener { exception ->
                            trySend(NetworkResponse.Error(exception.toString()))
                        }
                    }.addOnFailureListener {
                        trySend(NetworkResponse.Error(it.toString()))
                    }
            } else {
                trySend(NetworkResponse.NoConnection())
            }
            awaitClose {
                channel.close()
            }
        }.flowOn(Dispatchers.IO)

    override fun dislike(bookData: BookData): Flow<NetworkResponse<Long>> =
        callbackFlow<NetworkResponse<Long>> {
            val map: MutableMap<String, Any> = HashMap()
            val base = baseRoot.collection(Const.books).document(bookData.documentName)
            if (hasConnection(context)) {
                base.get()
                    .addOnSuccessListener {
                        map[Const.like] = (it.getLong(Const.like)!! - 1) as Any
                        if ((it.getLong(Const.like) as Long) != 0L) {
                            base.update(map).addOnSuccessListener {
                                trySend(NetworkResponse.Success((map[Const.like] as Long)))
                            }.addOnFailureListener { exception ->
                                trySend(NetworkResponse.Error(exception.toString()))
                            }
                        }
                    }.addOnFailureListener {
                        trySend(NetworkResponse.Error(it.toString()))
                    }

            } else {
                trySend(NetworkResponse.NoConnection())
            }
            awaitClose {
                channel.close()
            }
        }.flowOn(Dispatchers.IO)


    override fun downloadBook(link: String): Flow<UploadData> =
        callbackFlow<UploadData> {
            if (hasConnection(context)) {
                val storage = FirebaseStorage.getInstance()
                val ref = storage.reference.child("booksData/${link}.pdf")

                val file = File(context.filesDir, link)
                downTask = ref.getFile(file).addOnCompleteListener {
                    trySend(
                        UploadData.Complete(
                            file.absolutePath
                        )
                    )
                }
                    .addOnFailureListener {
                        trySend(UploadData.Failture)
                    }
                    .addOnProgressListener {
                        val progress = it.bytesTransferred * 100 / it.totalByteCount
                        l("progress${progress}")
                        trySend(UploadData.Progress(progress.toInt()))
                    }
                    .addOnCanceledListener {
                        trySend(UploadData.Cancel)
                    }
                    .addOnPausedListener {
                        trySend(UploadData.Pause)
                    }
                awaitClose()
            } else {
                trySend(UploadData.NoConnection)
            }
        }.flowOn(Dispatchers.IO)


    override fun pauseDownload() {
        if (::downTask.isInitialized)
            downTask.pause()
    }

    override fun resumeDownload() {
        if (::downTask.isInitialized)
            downTask.resume()
    }

    override fun cancelDownload() {
        if (::downTask.isInitialized)
            downTask.cancel()
    }
}