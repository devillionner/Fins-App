package com.vist.fins.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vist.fins.data.entities.Operation

@Dao
interface FinanceDatabaseDao {
    @Query("SELECT * from operation")
    suspend fun getAll(): LiveData<List<Operation>>


//    @Query("SELECT * from my_fins_operations_list where itemId = :id")
//    fun getById(id: Int) : FinancialOperationItem?

//    @Query("SELECT SUM(operationValue) FROM my_fins_operations_list WHERE operationType = :type")
//    fun getValuesByType(type: String): LiveData<Float>

//    @Query("SELECT SUM(operationValue) FROM operation WHERE operationType = '${OperationType.income}'")
//    suspend fun getValuesByIncome(): LiveData<Float>
//
//    @Query("SELECT SUM(operationValue) FROM operation WHERE operationType = '${OperationType.expense}'")
//    suspend fun getValuesByExpenses(): LiveData<Float>

    @Insert
    suspend fun insert(item: Operation)

    @Update
    suspend fun update(item: Operation)

    @Delete
    suspend fun delete(item: Operation)

    @Query("DELETE FROM operation")
     suspend fun deleteAllOperations()
}