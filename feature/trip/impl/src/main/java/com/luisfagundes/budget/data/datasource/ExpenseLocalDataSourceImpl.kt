package com.luisfagundes.budget.data.datasource

import com.luisfagundes.budget.data.dao.ExpenseDao
import com.luisfagundes.budget.data.model.ExpenseEntity
import javax.inject.Inject

internal class ExpenseLocalDataSourceImpl @Inject constructor(
    private val dao: ExpenseDao
) : ExpenseLocalDataSource {

    override suspend fun addExpense(entity: ExpenseEntity): Result<Unit> =
        runCatching { dao.insert(entity) }

    override suspend fun getExpensesByTripId(tripId: Int): Result<List<ExpenseEntity>> =
        runCatching { dao.getByTripId(tripId) }

    override suspend fun deleteExpense(entity: ExpenseEntity): Result<Unit> =
        runCatching { dao.delete(entity) }
}
