package com.luisfagundes.trip.domain.usecase

import com.luisfagundes.common.domain.usecase.GetUnsplashImageUseCase
import com.luisfagundes.trip.domain.repository.TripRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GetTripImageUseCaseTest {

    private val repository: TripRepository = mockk()
    private lateinit var useCase: GetUnsplashImageUseCase

    @BeforeEach
    fun setup() {
        useCase = GetUnsplashImageUseCase(repository)
    }

    @Test
    fun `invoke calls repository getTripImageUrl and returns success with image URL`() = runTest {
        // Given
        val location = "Paris, France"
        val expectedImageUrl = "https://images.unsplash.com/photo-123456"

        coEvery { repository.getTripImageUrl(location) } returns Result.success(expectedImageUrl)

        // When
        val result = useCase(location)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedImageUrl, result.getOrNull())
        coVerify(exactly = 1) { repository.getTripImageUrl(location) }
    }

    @Test
    fun `invoke calls repository getTripImageUrl and returns failure when repository fails`() = runTest {
        // Given
        val location = "Paris, France"
        val exception = RuntimeException("Network error")

        coEvery { repository.getTripImageUrl(location) } returns Result.failure(exception)

        // When
        val result = useCase(location)

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        coVerify(exactly = 1) { repository.getTripImageUrl(location) }
    }

    @Test
    fun `invoke passes correct location to repository`() = runTest {
        val location = "Rome, Italy"
        coEvery { repository.getTripImageUrl(location) } returns Result.success("https://example.com/image.jpg")

        useCase(location)

        coVerify(exactly = 1) { repository.getTripImageUrl("Rome, Italy") }
    }

    @Test
    fun `invoke handles empty location string`() = runTest {
        // Given
        val location = ""
        coEvery { repository.getTripImageUrl(location) } returns Result.success("https://example.com/default.jpg")

        // When
        val result = useCase(location)

        // Then
        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { repository.getTripImageUrl("") }
    }

    @Test
    fun `invoke handles location with special characters`() = runTest {
        // Given
        val location = "São Paulo, Brazil"
        val imageUrl = "https://example.com/sao-paulo.jpg"

        coEvery { repository.getTripImageUrl(location) } returns Result.success(imageUrl)

        // When
        val result = useCase(location)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(imageUrl, result.getOrNull())
        coVerify(exactly = 1) { repository.getTripImageUrl(location) }
    }
}
