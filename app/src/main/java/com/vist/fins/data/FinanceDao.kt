package com.vist.fins.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vist.fins.data.entities.Category
import com.vist.fins.data.entities.Operation
import com.vist.fins.data.entities.Subcategory
import com.vist.fins.data.entities.relations.CategoryWithOperations
import com.vist.fins.data.entities.relations.OperationSubcategoryCrossRef
import com.vist.fins.data.entities.relations.OperationWithSubcategories
import com.vist.fins.data.entities.relations.SubcategoryWithOperations
import kotlinx.coroutines.flow.Flow


@Dao
interface FinanceDao {
    @Transaction
    @Query("SELECT * from operation")
    fun getAll(): LiveData<List<Operation>>

    @Transaction
    @Query("SELECT * from category")
    fun getAllCategories(): LiveData<List<Category>>

    @Transaction
    @Query("SELECT SUM(operationValue) FROM operation WHERE operationType = '${OperationType.income}'")
    fun getAllIncomeValues(): LiveData<Float>

    @Transaction
    @Query("SELECT SUM(operationValue) FROM operation WHERE operationType = '${OperationType.expense}'")
    fun getAllExpenseValues(): LiveData<Float>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOperation(operation: Operation)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubcategory(subcategory: Subcategory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOperationSubcategoryCrossRef(crossRef: OperationSubcategoryCrossRef)

    @Query("DELETE FROM operation")
    suspend fun deleteAllOperations()

    @Query("DELETE FROM category")
    suspend fun deleteAllCategories()

    @Query("DELETE FROM subcategory")
    suspend fun deleteAllSubcategories()

    @Query("DELETE FROM operationSubcategoryCrossRef")
    suspend fun deleteAllOperationSubcategoryCrossRef()

    @Delete
    suspend fun delete(item: Operation)
//    @Transaction
//    @Query("SELECT SUM(operationValue) FROM operation WHERE operationType = :operationType")
//    fun getValuesByType(operationType: String): Flow<Float>

    @Transaction
    @Query("SELECT SUM(operationValue) FROM operation WHERE operationType = :operationType")
    suspend fun getValuesByOperationType(operationType: String): Float

    @Transaction
    @Query("SELECT * from category")
    fun getUpdatedAllCategories(): List<Category>

    @Transaction
    @Query("SELECT * FROM category WHERE categoryName = :categoryName")
    fun getCategoryWithOperations(categoryName: String): List<CategoryWithOperations>

    @Transaction
    @Query("SELECT * FROM subcategory WHERE subcategoryName = :subcategoryName")
    suspend fun getOperationsOfSubcategory(subcategoryName: String): List<SubcategoryWithOperations>

    @Transaction
    @Query("SELECT * FROM operation WHERE categoryName = :operationId")
    suspend fun getSubcategoriesOfOperation(operationId: Long): List<OperationWithSubcategories>
}