package com.luisfagundes.trip.domain.usecase

import com.luisfagundes.trip.domain.model.ValidationError
import com.luisfagundes.trip.domain.model.ValidationResult
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
    fun `invoke returns Invalid with MISSING_DATE when date is null`() {
        // Given
        val date: LocalDate? = null

        // When
        val result = useCase(date)

        // Then
        assertEquals(ValidationResult.Invalid(ValidationError.MISSING_DATE), result)
    }

    @Test
    fun `invoke returns Invalid with DATE_BEFORE_TODAY when date is before today`() {
        // Given
        val date = LocalDate.now().minusDays(1)

        // When
        val result = useCase(date)

        // Then
        assertEquals(ValidationResult.Invalid(ValidationError.DATE_BEFORE_TODAY), result)
    }

    @Test
    fun `invoke returns Invalid with DATE_BEFORE_TODAY when date is one year ago`() {
        // Given
        val date = LocalDate.now().minusYears(1)

        // When
        val result = useCase(date)

        // Then
        assertEquals(ValidationResult.Invalid(ValidationError.DATE_BEFORE_TODAY), result)
    }

    @Test
    fun `invoke returns Valid when date is today`() {
        // Given
        val date = LocalDate.now()

        // When
        val result = useCase(date)

        // Then
        assertEquals(ValidationResult.Valid, result)
    }

    @Test
    fun `invoke returns Valid when date is tomorrow`() {
        // Given
        val date = LocalDate.now().plusDays(1)

        // When
        val result = useCase(date)

        // Then
        assertEquals(ValidationResult.Valid, result)
    }

    @Test
    fun `invoke returns Valid when date is in the future`() {
        // Given
        val date = LocalDate.now().plusMonths(6)

        // When
        val result = useCase(date)

        // Then
        assertEquals(ValidationResult.Valid, result)
    }
}
