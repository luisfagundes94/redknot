package com.luisfagundes.trip.domain.usecase

import com.luisfagundes.common.domain.model.FieldValidationError
import com.luisfagundes.common.domain.model.FieldValidationResult
import com.luisfagundes.common.domain.usecase.ValidateDateUseCase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class ValidateDateUseCaseTest {

    private lateinit var useCase: ValidateDateUseCase

    @BeforeEach
    fun setup() {
        useCase = ValidateDateUseCase()
    }

    @Test
    fun `invoke returns Invalid with EMPTY when date is null`() {
        // Given
        val error = FieldValidationError.EMPTY
        val date: LocalDate? = null

        // When
        val result = useCase(date)

        // Then
        assertEquals(FieldValidationResult.Invalid(error), result)
    }

    @Test
    fun `invoke returns Invalid with DATE_IN_THE_PAST when date is before today`() {
        // Given
        val error = FieldValidationError.DATE_IN_THE_PAST
        val date = LocalDate.now().minusDays(1)

        // When
        val result = useCase(date)

        // Then
        assertEquals(FieldValidationResult.Invalid(error), result)
    }

    @Test
    fun `invoke returns Invalid with DATE_IN_THE_PAST when date is one year ago`() {
        // Given
        val error = FieldValidationError.DATE_IN_THE_PAST
        val date = LocalDate.now().minusYears(1)

        // When
        val result = useCase(date)

        // Then
        assertEquals(FieldValidationResult.Invalid(error), result)
    }

    @Test
    fun `invoke returns Valid when date is today`() {
        // Given
        val date = LocalDate.now()

        // When
        val result = useCase(date)

        // Then
        assertEquals(FieldValidationResult.Valid, result)
    }

    @Test
    fun `invoke returns Valid when date is tomorrow`() {
        // Given
        val date = LocalDate.now().plusDays(1)

        // When
        val result = useCase(date)

        // Then
        assertEquals(FieldValidationResult.Valid, result)
    }

    @Test
    fun `invoke returns Valid when date is in the future`() {
        // Given
        val date = LocalDate.now().plusMonths(6)

        // When
        val result = useCase(date)

        // Then
        assertEquals(FieldValidationResult.Valid, result)
    }
}
