package com.vist.fins.data.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.vist.fins.data.entities.Category
import com.vist.fins.data.entities.Operation

data class CategoryWithOperations(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "categoryName",
        entityColumn = "categoryName"
    )
    val operations: List<Operation>
)