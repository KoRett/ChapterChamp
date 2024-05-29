package com.korett.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book")
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo("poster_url") val posterUrl: String,
    val title: String,
    val description: String,
    val author: String,
    val rating: Float,
    val isFavourite: Boolean
)