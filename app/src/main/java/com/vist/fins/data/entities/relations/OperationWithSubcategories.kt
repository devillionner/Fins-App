package com.vist.fins.data.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.vist.fins.data.entities.Operation
import com.vist.fins.data.entities.Subcategory

data class OperationWithSubcategories(
    @Embedded val operation: Operation,
    @Relation(
        parentColumn = "operationId",
        entityColumn = "subcategoryName",
        associateBy = Junction(OperationSubcategoryCrossRef::class)
    )
    val subcategories: List<Subcategory>
)