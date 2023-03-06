package uz.gita.bookappShoxruh.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [BookEntity::class], // Tell the database the entries will hold data of this type
    version = 1
)

abstract class AppDataBase : RoomDatabase() {

    abstract fun bookDao(): BookDao
}