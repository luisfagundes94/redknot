package com.luisfagundes.budget.domain.model

import java.math.BigDecimal
import java.time.LocalDate

internal data class Expense(
    val amount: BigDecimal,
    val category: ExpenseCategory,
    val date: LocalDate,
    val description: String?
)
