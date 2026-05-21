package com.luisfagundes.budget.domain.model

internal enum class BudgetCurrency(val symbol: String, val code: String) {
    EUR("€", "EUR"),
    USD("$", "USD"),
    GBP("£", "GBP"),
    BRL("R$", "BRL"),
    JPY("¥", "JPY"),
    CAD("CA$", "CAD"),
    AUD("A$", "AUD"),
    CHF("Fr", "CHF"),
    INR("₹", "INR")
}
