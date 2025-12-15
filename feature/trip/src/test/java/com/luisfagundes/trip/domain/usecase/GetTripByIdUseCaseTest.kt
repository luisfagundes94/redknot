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

internal class GetTripByIdUseCaseTest {

    private val repository: TripRepository = mockk()
    private lateinit var useCase: GetTripByIdUseCase

    @BeforeEach
    fun setup() {
        useCase = GetTripByIdUseCase(repository)
    }

    @Test
    fun `invoke returns trip with calculated PAST status when endDate is before today`() = runTest {
        // Given
        val tripId = 1
        val trip = createTrip(
            id = tripId,
            startDate = LocalDate.of(2024, 11, 1),
            endDate = LocalDate.of(2024, 11, 10)
        )

        coEvery { repository.getTripById(tripId) } returns Result.success(trip)

        // When
        val result = useCase(tripId)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(TripStatus.PAST, result.getOrNull()?.status)
        coVerify(exactly = 1) { repository.getTripById(tripId) }
    }

    @Test
    fun `invoke returns trip with calculated UPCOMING status when startDate is after today`() = runTest {
        // Given
        val tripId = 2
        val trip = createTrip(
            id = tripId,
            startDate = LocalDate.of(2025, 6, 1),
            endDate = LocalDate.of(2025, 6, 10)
        )

        coEvery { repository.getTripById(tripId) } returns Result.success(trip)

        // When
        val result = useCase(tripId)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(TripStatus.UPCOMING, result.getOrNull()?.status)
        coVerify(exactly = 1) { repository.getTripById(tripId) }
    }

    @Test
    fun `invoke returns trip with calculated ONGOING status when today is between start and end`() = runTest {
        // Given
        val tripId = 3
        val today = LocalDate.now()
        val trip = createTrip(
            id = tripId,
            startDate = today.minusDays(5),
            endDate = today.plusDays(5)
        )

        coEvery { repository.getTripById(tripId) } returns Result.success(trip)

        // When
        val result = useCase(tripId)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(TripStatus.ONGOING, result.getOrNull()?.status)
        coVerify(exactly = 1) { repository.getTripById(tripId) }
    }

    @Test
    fun `invoke returns failure when repository fails`() = runTest {
        // Given
        val tripId = 999
        val exception = RuntimeException("Trip not found")

        coEvery { repository.getTripById(tripId) } returns Result.failure(exception)

        // When
        val result = useCase(tripId)

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        coVerify(exactly = 1) { repository.getTripById(tripId) }
    }

    @Test
    fun `invoke preserves all trip properties during transformation`() = runTest {
        // Given
        val tripId = 123
        val originalTrip = createTrip(
            id = tripId,
            title = "Summer Vacation",
            location = "Paris, France",
            startDate = LocalDate.of(2025, 6, 15),
            endDate = LocalDate.of(2025, 6, 25),
            imageUrl = "https://example.com/paris.jpg"
        )

        coEvery { repository.getTripById(tripId) } returns Result.success(originalTrip)

        // When
        val result = useCase(tripId)

        // Then
        assertTrue(result.isSuccess)

        val trip = result.getOrNull()!!
        assertEquals(originalTrip, trip)
    }

    @Test
    fun `invoke recalculates status even when trip already has a status`() = runTest {
        // Given
        val tripId = 5
        val trip = createTrip(
            id = tripId,
            startDate = LocalDate.of(2025, 6, 1),
            endDate = LocalDate.of(2025, 6, 10),
            status = TripStatus.PAST
        )

        coEvery { repository.getTripById(tripId) } returns Result.success(trip)

        // When
        val result = useCase(tripId)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(TripStatus.UPCOMING, result.getOrNull()?.status)
    }

    @Test
    fun `invoke passes correct id to repository`() = runTest {
        // Given
        val tripId = 42
        val trip = createTrip(id = tripId)

        coEvery { repository.getTripById(tripId) } returns Result.success(trip)

        // When
        useCase(tripId)

        // Then
        coVerify(exactly = 1) { repository.getTripById(42) }
    }

    private fun createTrip(
        id: Int = 1,
        title: String = "Test Trip",
        location: String = "Test Location",
        startDate: LocalDate = LocalDate.now().plusDays(7),
        endDate: LocalDate = LocalDate.now().plusDays(14),
        imageUrl: String = "https://example.com/image.jpg",
        status: TripStatus = TripStatus.UNSCHEDULED
    ) = Trip(
        id = id,
        title = title,
        location = location,
        startDate = startDate,
        endDate = endDate,
        imageUrl = imageUrl,
        status = status
    )
}
