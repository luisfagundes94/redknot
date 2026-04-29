package com.luisfagundes.trip.domain.usecase

import com.luisfagundes.common.domain.model.CommonFieldError
import com.luisfagundes.common.domain.model.FieldValidationResult
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ValidateDestinationUseCaseTest {

    private lateinit var useCase: ValidateDestinationUseCase

    @BeforeEach
    fun setup() {
        useCase = ValidateDestinationUseCase()
    }

    @Test
    fun `invoke returns Valid when destination is valid without numbers`() {
        // Given
        val destination = "Paris, France"

        // When
        val result = useCase(destination)

        // Then
        assertEquals(FieldValidationResult.Valid, result)
    }

    @Test
    fun `invoke returns Invalid with EMPTY when destination is empty string`() {
        // Given
        val error = FieldValidationResult.Invalid(CommonFieldError.EMPTY)
        val destination = ""

        // When
        val result = useCase(destination)

        // Then
        assertEquals(error, result)
    }

    @Test
    fun `invoke returns Invalid with EMPTY when destination is blank with spaces`() {
        // Given
        val error = FieldValidationResult.Invalid(CommonFieldError.EMPTY)
        val destination = "   "

        // When
        val result = useCase(destination)

        // Then
        assertEquals(error, result)
    }

    @Test
    fun `invoke returns Invalid with EMPTY when destination contains only tabs and newlines`() {
        // Given
        val error = FieldValidationResult.Invalid(CommonFieldError.EMPTY)
        val destination = "\t\n"

        // When
        val result = useCase(destination)

        // Then
        assertEquals(error, result)
    }

    @Test
    fun `invoke returns Invalid with INVALID_DESTINATION_FORMAT when destination contains digits`() {
        // Given
        val error = FieldValidationResult.Invalid(CommonFieldError.CONTAINS_NUMBER)
        val destination = "Paris123"

        // When
        val result = useCase(destination)

        // Then
        assertEquals(error, result)
    }

    @Test
    fun `invoke returns Invalid with INVALID_DESTINATION_FORMAT when destination contains single digit`() {
        // Given
        val error = FieldValidationResult.Invalid(CommonFieldError.CONTAINS_NUMBER)
        val destination = "Paris 1"

        // When
        val result = useCase(destination)

        // Then
        assertEquals(error, result)
    }

    @Test
    fun `invoke returns Invalid with INVALID_DESTINATION_FORMAT when destination starts with digit`() {
        // Given
        val error = FieldValidationResult.Invalid(CommonFieldError.CONTAINS_NUMBER)
        val destination = "1 Paris Street"

        // When
        val result = useCase(destination)

        // Then
        assertEquals(error, result)
    }

    @Test
    fun `invoke returns Valid when destination has special characters but no digits`() {
        // Given
        val destination = "São Paulo, Brazil"

        // When
        val result = useCase(destination)

        // Then
        assertEquals(FieldValidationResult.Valid, result)
    }

    @Test
    fun `invoke returns Valid when destination has hyphens and commas`() {
        // Given
        val destination = "Saint-Tropez, France"

        // When
        val result = useCase(destination)

        // Then
        assertEquals(FieldValidationResult.Valid, result)
    }

    @Test
    fun `invoke returns Valid when destination is single word`() {
        // Given
        val destination = "Tokyo"

        // When
        val result = useCase(destination)

        // Then
        assertEquals(FieldValidationResult.Valid, result)
    }
}
