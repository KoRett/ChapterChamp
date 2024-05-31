package com.korett.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.korett.data.database.entity.BookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query("SELECT * FROM book")
    suspend fun getAllBooks(): List<BookEntity>

    @Query("SELECT * FROM book WHERE isFavourite LIKE 1")
    fun getFavouriteBooks(): Flow<List<BookEntity>>

    @Query("UPDATE book SET isFavourite = :isFavourite WHERE id LIKE :bookId")
    suspend fun updateBookIsFavouriteById(bookId: Int, isFavourite: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBook(bookEntity: BookEntity)
}