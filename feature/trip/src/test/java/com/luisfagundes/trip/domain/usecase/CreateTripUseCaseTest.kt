package com.luisfagundes.trip.domain.usecase

import com.luisfagundes.trip.domain.repository.TripRepository
import com.luisfagundes.trip.presentation.fixtures.fakeTrip
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class CreateTripUseCaseTest {

    private val repository: TripRepository = mockk()
    private lateinit var useCase: CreateTripUseCase

    @BeforeEach
    fun setup() {
        useCase = CreateTripUseCase(repository)
    }

    @Test
    fun `invoke calls repository createTrip and returns success`() = runTest {
        // Given
        coEvery { repository.createTrip(fakeTrip) } returns Result.success(Unit)

        // When
        val result = useCase(fakeTrip)

        // Then
        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { repository.createTrip(fakeTrip) }
    }

    @Test
    fun `invoke calls repository createTrip and returns failure when repository fails`() = runTest {
        // Given
        val exception = RuntimeException("Database error")

        coEvery { repository.createTrip(fakeTrip) } returns Result.failure(exception)

        // When
        val result = useCase(fakeTrip)

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        coVerify(exactly = 1) { repository.createTrip(fakeTrip) }
    }

    @Test
    fun `invoke passes correct trip data to repository`() = runTest {
        // Given
        val trip = fakeTrip.copy(
            id = 42,
            title = "Custom Title",
            location = "Custom Location"
        )
        coEvery { repository.createTrip(trip) } returns Result.success(Unit)

        // When
        useCase(trip)

        // Then
        coVerify(exactly = 1) {
            repository.createTrip(
                match {
                    it.id == 42 &&
                    it.title == "Custom Title" &&
                    it.location == "Custom Location"
                }
            )
        }
    }
}
