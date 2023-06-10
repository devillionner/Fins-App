package com.vist.fins.data.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.vist.fins.data.entities.Operation
import com.vist.fins.data.entities.Subcategory

data class SubcategoryWithOperations(
    @Embedded val subcategory: Subcategory,
    @Relation(
        parentColumn = "subcategoryName",
        entityColumn = "operationId",
        associateBy = Junction(OperationSubcategoryCrossRef::class)
    )
    val operations: List<Operation>
)