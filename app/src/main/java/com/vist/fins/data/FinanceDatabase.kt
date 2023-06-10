package com.vist.fins.data

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vist.fins.data.entities.Category
import com.vist.fins.data.entities.Operation
import com.vist.fins.data.entities.Subcategory
import com.vist.fins.data.entities.relations.OperationSubcategoryCrossRef

@Database(entities = [
        Operation::class,
        Category::class,
        Subcategory::class,
        OperationSubcategoryCrossRef::class
    ],
    version = 1,
//    autoMigrations = [
//        AutoMigration(from = 1, to = 2)
//    ]
//    exportSchema = false
)
abstract class FinanceDatabase : RoomDatabase() {

    abstract val financeDao: FinanceDao

    companion object {
        @Volatile
        private var INSTANCE: FinanceDatabase? = null

        fun getInstance(context: Context): FinanceDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    FinanceDatabase::class.java,
                    "finance_db"
                ).createFromAsset("database/category.db").addMigrations().build().also {
                    INSTANCE = it
                }
            }
        }
    }
}