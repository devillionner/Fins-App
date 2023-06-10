package com.vist.fins.data.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Category(
    val categoryName: String,
//    val categoryType: String,
    val imageRes: String,
    @PrimaryKey(autoGenerate = true)
    val categoryId: Long = 0L


//    @ColumnInfo(name = "operationType")
//    var operationType: String,
//
//    @ColumnInfo(name = "operationCategory")
//    var operationCategory: String,
)
