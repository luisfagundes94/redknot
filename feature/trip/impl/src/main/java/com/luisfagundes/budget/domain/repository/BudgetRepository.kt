package com.luisfagundes.budget.domain.repository

import com.luisfagundes.budget.domain.model.BudgetCurrency
import com.luisfagundes.budget.domain.model.Expense
import java.math.BigDecimal

internal interface BudgetRepository {
    suspend fun getExpensesByTripId(tripId: Int): Result<List<Expense>>
    suspend fun addExpense(tripId: Int, expense: Expense): Result<Unit>
    suspend fun deleteExpense(tripId: Int, expense: Expense): Result<Unit>
    suspend fun setTotalBudget(tripId: Int, totalBudget: BigDecimal): Result<Unit>
    suspend fun getTotalBudget(tripId: Int): Result<BigDecimal?>
    suspend fun setCurrency(tripId: Int, currency: BudgetCurrency): Result<Unit>
    suspend fun getCurrency(tripId: Int): Result<BudgetCurrency>
}
