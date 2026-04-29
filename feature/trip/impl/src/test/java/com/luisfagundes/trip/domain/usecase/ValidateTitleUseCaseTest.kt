package com.luisfagundes.trip.domain.usecase

import com.luisfagundes.common.domain.model.CommonFieldError
import com.luisfagundes.common.domain.model.FieldValidationResult
import com.luisfagundes.common.domain.usecase.ValidateTitleUseCase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ValidateTitleUseCaseTest {

    private lateinit var useCase: ValidateTitleUseCase

    @BeforeEach
    fun setup() {
        useCase = ValidateTitleUseCase()
    }

    @Test
    fun `invoke returns Valid when title is not blank`() {
        // Given
        val title = "Summer In Italy"

        // When
        val result = useCase(title)

        // Then
        assertEquals(FieldValidationResult.Valid, result)
    }

    @Test
    fun `invoke returns Invalid with EMPTY when title is empty string`() {
        // Given
        val error = FieldValidationResult.Invalid(CommonFieldError.EMPTY)
        val title = ""

        // When
        val result = useCase(title)

        // Then
        assertEquals(error, result)
    }

    @Test
    fun `invoke returns Invalid with EMPTY when title is blank with spaces`() {
        // Given
        val error = FieldValidationResult.Invalid(CommonFieldError.EMPTY)
        val title = "   "

        // When
        val result = useCase(title)

        // Then
        assertEquals(error, result)
    }

    @Test
    fun `invoke returns Invalid with EMPTY when title contains only tabs and newlines`() {
        // Given
        val error = FieldValidationResult.Invalid(CommonFieldError.EMPTY)
        val title = "\t\n"

        // When
        val result = useCase(title)

        // Then
        assertEquals(error, result)
    }

    @Test
    fun `invoke returns Valid when title has leading and trailing spaces but content in between`() {
        // Given
        val title = "  Valid Title  "

        // When
        val result = useCase(title)

        // Then
        assertEquals(FieldValidationResult.Valid, result)
    }
}
