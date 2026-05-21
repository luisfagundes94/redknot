package com.luisfagundes.budget.data.mapper

import com.luisfagundes.budget.data.model.ExpenseEntity
import com.luisfagundes.budget.domain.model.Expense
import com.luisfagundes.budget.domain.model.ExpenseCategory
import java.math.BigDecimal
import java.time.LocalDate
import javax.inject.Inject

internal class ExpenseMapper @Inject constructor() {
    fun mapToEntity(expense: Expense, tripId: Int): ExpenseEntity = ExpenseEntity(
        id = expense.id,
        tripId = tripId,
        amount = expense.amount.toPlainString(),
        category = expense.category.name,
        date = expense.date.toString(),
        description = expense.description
    )

    fun mapToDomain(entity: ExpenseEntity): Expense = Expense(
        id = entity.id,
        amount = BigDecimal(entity.amount),
        category = ExpenseCategory.valueOf(entity.category),
        date = LocalDate.parse(entity.date),
        description = entity.description
    )
}
