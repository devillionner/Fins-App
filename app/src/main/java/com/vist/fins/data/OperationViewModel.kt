package com.vist.fins.data

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.vist.fins.data.entities.Category
import com.vist.fins.data.entities.Operation
import com.vist.fins.data.entities.Subcategory
import com.vist.fins.data.entities.relations.OperationSubcategoryCrossRef
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class OperationViewModel(application: Application) : AndroidViewModel(application) {
    val readAllData: LiveData<List<Operation>>
    val readAllCategories: LiveData<List<Category>>
    val readAllIncomeValues : LiveData<Float>
    val readAllExpenseValues : LiveData<Float>

    private val repository: FinanceRepository

    init {
        val financeDao = FinanceDatabase.getInstance(application).financeDao
        repository = FinanceRepository(financeDao)

        readAllData = repository.readAllData
        readAllCategories = repository.readAllCategories
        readAllIncomeValues = repository.readAllIncomeValues
        readAllExpenseValues = repository.readAllExpenseValues
//        getCategoryWithOperations = repository.getCategoryWithOperations
//        getFinancialOperationValuesByIncome = repository.getFinancialOperationValuesByIncome
//        getFinancialOperationValuesByExpenses = repository.getFinancialOperationValuesByExpenses
    }


//    fun getFinancialOperationById(financialOperationId: Int) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.getFinancialOperationById(financialOperationId)
//        }
//    }

//    fun getFinancialOperationValuesByType(type: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.getFinancialOperationValuesByType(type)
//        }
//    }

    fun insertCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertCategory(category)
        }
    }

    fun insertOperation(operation: Operation) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertOperation(operation)
        }
    }

    fun insertSubcategory(subcategory: Subcategory) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertSubcategory(subcategory)
        }
    }

    fun insertOperationSubcategoryCrossRef(operationSubcategoryCrossRef: OperationSubcategoryCrossRef) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertOperationSubcategoryCrossRef(operationSubcategoryCrossRef)
        }
    }

    fun deleteAllOperations() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllOperations()
        }
    }

    fun deleteAllCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllCategories()
        }
    }

    fun deleteAllSubcategories() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllSubcategories()
        }
    }

    fun deleteAllOperationSubcategoryCrossRef() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllOperationSubcategoryCrossRef()
        }
    }

    fun deleteFinancialOperation(financialOperationItem: Operation) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFinancialOperation(financialOperationItem = financialOperationItem)
        }
    }

//    GET
//    fun getValuesByType(operationType: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.getValuesByType(operationType).collect { response ->
//                if (operationType == OperationType.income) {
//                    allFormIncome = response
//                } else allFormExpense = response
//            }
//        }
//    }
////        return allFormSets

//
    fun getCategoryWithOperations(categoryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCategoryWithOperations(categoryName)
        }
    }
//
//    fun getOperationsOfSubcategory(subcategoryName: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.getOperationsOfSubcategory(subcategoryName)
//        }
//    }
//
//    fun getSubcategoriesOfOperation(operationId: Long) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.getSubcategoriesOfOperation(operationId)
//        }
//    }

}




//    fun addFinancialOperation(financialOperationItem: Operation) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.addFinancialOperation(financialOperationItem)
//        }
//    }
//
//    fun updateFinancialOperation(financialOperationItem: Operation) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.updateFinancialOperation(financialOperationItem = financialOperationItem)
//        }
//    }
//
//    fun deleteFinancialOperation(financialOperationItem: Operation) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.deleteFinancialOperation(financialOperationItem = financialOperationItem)
//        }
//    }
//
//    fun deleteAllOperations() {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.deleteAllOperations()
//        }
//    }


class OperationViewModelFactory(
    private val application: Application,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(OperationViewModel::class.java)) {
            return OperationViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
