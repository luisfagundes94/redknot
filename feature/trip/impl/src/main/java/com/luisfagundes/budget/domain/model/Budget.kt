package com.luisfagundes.budget.domain.model

import java.math.BigDecimal

internal data class Budget(
    val total: BigDecimal,
    val spent: BigDecimal,
    val remaining: BigDecimal,
    val currency: BudgetCurrency = BudgetCurrency.EUR
)
