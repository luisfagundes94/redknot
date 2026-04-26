package com.luisfagundes.trip.domain.usecase

import com.luisfagundes.trip.domain.model.TripStatus
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
        val trip = fakeTrip.copy(
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
        val trip = fakeTrip.copy(
            id = tripId,
            startDate = LocalDate.now().plusDays(5),
            endDate = LocalDate.now().plusDays(10)
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
        val trip = fakeTrip.copy(
            id = tripId,
            startDate = LocalDate.now().minusDays(5),
            endDate = LocalDate.now().plusDays(5)
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
        val originalTrip = fakeTrip.copy(
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

        val trip = result.getOrNull() ?: return@runTest

        assertEquals(123, trip.id)
        assertEquals("Summer Vacation", trip.title)
        assertEquals("Paris, France", trip.location)
        assertEquals(LocalDate.of(2025, 6, 15), trip.startDate)
        assertEquals(LocalDate.of(2025, 6, 25), trip.endDate)
        assertEquals("https://example.com/paris.jpg", trip.imageUrl)
    }

    @Test
    fun `invoke recalculates status even when trip already has a status`() = runTest {
        // Given
        val tripId = 5
        val trip = fakeTrip.copy(
            id = tripId,
            startDate = LocalDate.now().plusDays(5),
            endDate = LocalDate.now().plusDays(10),
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
        val trip = fakeTrip.copy(id = tripId)

        coEvery { repository.getTripById(tripId) } returns Result.success(trip)

        // When
        useCase(tripId)

        // Then
        coVerify(exactly = 1) { repository.getTripById(42) }
    }
}
