package com.luisfagundes.trip.domain.usecase

import com.luisfagundes.trip.domain.model.Trip
import com.luisfagundes.trip.domain.model.TripStatus
import com.luisfagundes.trip.domain.repository.TripRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

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
        val trip = createTestTrip()
        coEvery { repository.createTrip(trip) } returns Result.success(Unit)

        // When
        val result = useCase(trip)

        // Then
        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { repository.createTrip(trip) }
    }

    @Test
    fun `invoke calls repository createTrip and returns failure when repository fails`() = runTest {
        // Given
        val trip = createTestTrip()
        val exception = RuntimeException("Database error")

        coEvery { repository.createTrip(trip) } returns Result.failure(exception)

        // When
        val result = useCase(trip)

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        coVerify(exactly = 1) { repository.createTrip(trip) }
    }

    @Test
    fun `invoke passes correct trip data to repository`() = runTest {
        // Given
        val trip = createTestTrip(
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

    private fun createTestTrip(
        id: Int = 1,
        title: String = "Test Trip",
        location: String = "Test Location"
    ) = Trip(
        id = id,
        title = title,
        location = location,
        startDate = LocalDate.now().plusDays(7),
        endDate = LocalDate.now().plusDays(14),
        imageUrl = "https://example.com/image.jpg",
        status = TripStatus.UPCOMING
    )
}
