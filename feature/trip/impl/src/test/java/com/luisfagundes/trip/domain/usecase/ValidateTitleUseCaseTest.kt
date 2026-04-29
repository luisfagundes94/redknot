package com.luisfagundes.trip.domain.usecase

import com.luisfagundes.common.domain.model.ValidationError
import com.luisfagundes.common.domain.model.ValidationResult
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
        assertEquals(ValidationResult.Valid, result)
    }

    @Test
    fun `invoke returns Invalid with EMPTY_TITLE when title is empty string`() {
        // Given
        val title = ""

        // When
        val result = useCase(title)

        // Then
        assertEquals(ValidationResult.Invalid(ValidationError.EMPTY_TITLE), result)
    }

    @Test
    fun `invoke returns Invalid with EMPTY_TITLE when title is blank with spaces`() {
        // Given
        val title = "   "

        // When
        val result = useCase(title)

        // Then
        assertEquals(ValidationResult.Invalid(ValidationError.EMPTY_TITLE), result)
    }

    @Test
    fun `invoke returns Invalid with EMPTY_TITLE when title contains only tabs and newlines`() {
        // Given
        val title = "\t\n"

        // When
        val result = useCase(title)

        // Then
        assertEquals(ValidationResult.Invalid(ValidationError.EMPTY_TITLE), result)
    }

    @Test
    fun `invoke returns Valid when title has leading and trailing spaces but content in between`() {
        // Given
        val title = "  Valid Title  "

        // When
        val result = useCase(title)

        // Then
        assertEquals(ValidationResult.Valid, result)
    }
}
