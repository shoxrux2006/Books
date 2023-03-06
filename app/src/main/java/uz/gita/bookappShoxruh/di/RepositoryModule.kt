package uz.gita.bookappShoxruh.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.bookappShoxruh.domain.BooksRepository
import uz.gita.bookappShoxruh.domain.impl.BooksRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @[Binds
    Singleton]
    fun provideBookRepo(booksRepositoryImpl: BooksRepositoryImpl): BooksRepository
//    @[Binds
//    Singleton]
//    fun provideCardRepo(authRepositoryImpl: CardRepositoryImpl): CardRepository
}