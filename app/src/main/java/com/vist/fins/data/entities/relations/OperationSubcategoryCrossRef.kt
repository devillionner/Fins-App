package com.vist.fins.data.entities.relations

import androidx.room.Entity

@Entity(primaryKeys = ["operationId", "subcategoryName"])
data class OperationSubcategoryCrossRef(
    val operationId: Long,
    val subcategoryName: String
)