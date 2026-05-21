package com.luisfagundes.budget.data.datasource

import com.luisfagundes.budget.data.model.ExpenseEntity

internal interface ExpenseLocalDataSource {
    suspend fun addExpense(entity: ExpenseEntity): Result<Unit>
    suspend fun getExpensesByTripId(tripId: Int): Result<List<ExpenseEntity>>
    suspend fun deleteExpense(entity: ExpenseEntity): Result<Unit>
}
