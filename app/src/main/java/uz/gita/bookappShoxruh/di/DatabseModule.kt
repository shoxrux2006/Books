package uz.gita.bookappShoxruh.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.bookappShoxruh.data.room.AppDataBase
import uz.gita.bookappShoxruh.data.room.BookDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabseModule {
    @Singleton
    @Provides
    fun provideAppDataBase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AppDataBase::class.java,
        "book.db"
    ).build()


    @Singleton
    @Provides
    fun provideBookDao(db: AppDataBase):BookDao = db.bookDao()
}