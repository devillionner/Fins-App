package com.vist.fins.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Subcategory(
    @PrimaryKey(autoGenerate = false)
    val subcategoryName: String
)