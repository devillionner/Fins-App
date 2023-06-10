package com.vist.fins.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.Date

@Entity
data class Operation(
    @PrimaryKey(autoGenerate = true)
    var operationId: Long = 0L,
    var categoryName: String,
    var operationValue: Float,
    var operationType: String,
    var operationDate: String
)
