package com.vist.fins.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.vist.fins.data.entities.Category
import com.vist.fins.data.entities.Operation
import com.vist.fins.data.entities.Subcategory
import com.vist.fins.data.entities.relations.CategoryWithOperations
import com.vist.fins.data.entities.relations.OperationSubcategoryCrossRef
import kotlinx.coroutines.flow.Flow

class FinanceRepository(private val financeDao: FinanceDao) {
    val readAllData : LiveData<List<Operation>> = financeDao.getAll()
    val readAllCategories : LiveData<List<Category>> = financeDao.getAllCategories()
    val readAllIncomeValues : LiveData<Float> = financeDao.getAllIncomeValues()
    val readAllExpenseValues : LiveData<Float> = financeDao.getAllExpenseValues()
//    val getCategoryWithOperations : List<CategoryWithOperations> = financeDao.getCategoryWithOperations(categoryName = )
//    val getFinancialOperationValuesByIncome : LiveData<Float> = financeDatabaseDao.getValuesByIncome()
//    val getFinancialOperationValuesByExpenses : LiveData<Float> = financeDatabaseDao.getValuesByExpenses()

//    suspend fun getFinancialOperationById(id: Int) {
//        financeDatabaseDao.getById((id))
//    }

//    fun getFinancialOperationValuesByType(type: String) {
//        financeDatabaseDao.getValuesByType((type))
//    }


    suspend fun insertCategory(category: Category) {
        financeDao.insertCategory((category))
    }

    suspend fun insertOperation(operation: Operation) {
        financeDao.insertOperation((operation))
    }

    suspend fun insertSubcategory(subcategory: Subcategory) {
        financeDao.insertSubcategory((subcategory))
    }

    suspend fun insertOperationSubcategoryCrossRef(operationSubcategoryCrossRef: OperationSubcategoryCrossRef) {
        financeDao.insertOperationSubcategoryCrossRef((operationSubcategoryCrossRef))
    }

    suspend fun deleteAllOperations() {
        financeDao.deleteAllOperations()
    }

    suspend fun deleteAllCategories() {
        financeDao.deleteAllCategories()
    }

    suspend fun deleteAllSubcategories() {
        financeDao.deleteAllSubcategories()
    }

    suspend fun deleteAllOperationSubcategoryCrossRef() {
        financeDao.deleteAllOperationSubcategoryCrossRef()
    }

    suspend fun deleteFinancialOperation(financialOperationItem: Operation) {
        financeDao.delete((financialOperationItem))
    }

////    GET
//    suspend fun getValuesByType(operationType: String): Flow<Float> = financeDao.getValuesByType(operationType)
//
    suspend fun getValuesByOperationType(operationType: String): Float {
        return financeDao.getValuesByOperationType((operationType))
    }
//
    suspend fun getCategoryWithOperations(categoryName: String) {
        financeDao.getCategoryWithOperations((categoryName))
    }
//
//    suspend fun getOperationsOfSubcategory(subcategoryName: String) {
//        financeDao.getOperationsOfSubcategory((subcategoryName))
//    }
//
//    suspend fun getSubcategoriesOfOperation(operationId: Long) {
//        financeDao.getSubcategoriesOfOperation((operationId))
//    }

//    suspend fun addFinancialOperation(financialOperationItem: Operation) {
//        financeDatabaseDao.insert((financialOperationItem))
//    }
//
//    suspend fun updateFinancialOperation(financialOperationItem: Operation) {
//        financeDatabaseDao.update((financialOperationItem))
//    }
//
//    suspend fun deleteFinancialOperation(financialOperationItem: Operation) {
//        financeDatabaseDao.delete((financialOperationItem))
//    }
//
//    suspend fun deleteAllOperations() {
//        financeDatabaseDao.deleteAllOperations()
//    }
}