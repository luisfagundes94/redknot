package com.luisfagundes.budget.data.repository

import com.luisfagundes.budget.data.datasource.ExpenseLocalDataSource
import com.luisfagundes.budget.data.mapper.ExpenseMapper
import com.luisfagundes.budget.domain.model.BudgetCurrency
import com.luisfagundes.budget.domain.model.Expense
import com.luisfagundes.budget.domain.repository.BudgetRepository
import com.luisfagundes.trip.data.dao.TripDao
import java.math.BigDecimal
import javax.inject.Inject

internal class BudgetRepositoryImpl @Inject constructor(
    private val dataSource: ExpenseLocalDataSource,
    private val mapper: ExpenseMapper,
    private val tripDao: TripDao
) : BudgetRepository {

    override suspend fun getExpensesByTripId(tripId: Int): Result<List<Expense>> =
        dataSource.getExpensesByTripId(tripId).map { entities ->
            entities.map(mapper::mapToDomain)
        }

    override suspend fun addExpense(tripId: Int, expense: Expense): Result<Unit> =
        dataSource.addExpense(mapper.mapToEntity(expense, tripId))

    override suspend fun deleteExpense(tripId: Int, expense: Expense): Result<Unit> =
        dataSource.deleteExpense(mapper.mapToEntity(expense, tripId))

    override suspend fun setTotalBudget(tripId: Int, totalBudget: BigDecimal): Result<Unit> =
        runCatching { tripDao.updateTotalBudget(tripId, totalBudget.toPlainString()) }

    override suspend fun getTotalBudget(tripId: Int): Result<BigDecimal?> =
        runCatching { tripDao.getTripById(tripId).totalBudget?.let { BigDecimal(it) } }

    override suspend fun setCurrency(tripId: Int, currency: BudgetCurrency): Result<Unit> =
        runCatching { tripDao.updateCurrency(tripId, currency.name) }

    override suspend fun getCurrency(tripId: Int): Result<BudgetCurrency> = runCatching {
        val name = tripDao.getTripById(tripId).currency
        BudgetCurrency.entries.firstOrNull { it.name == name } ?: BudgetCurrency.EUR
    }
}
