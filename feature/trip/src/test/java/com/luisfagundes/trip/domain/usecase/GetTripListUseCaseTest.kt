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

internal class GetTripListUseCaseTest {

    private val repository: TripRepository = mockk()
    private lateinit var useCase: GetTripListUseCase

    @BeforeEach
    fun setup() {
        useCase = GetTripListUseCase(repository)
    }

    @Test
    fun `invoke returns empty map when repository returns empty list`() = runTest {
        // Given
        coEvery { repository.getTripList() } returns Result.success(emptyList())

        // When
        val result = useCase()

        // Then
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()?.isEmpty() == true)
        coVerify(exactly = 1) { repository.getTripList() }
    }

    @Test
    fun `invoke calculates status and groups trips correctly`() = runTest {
        // Given
        val pastTrip = createTrip(
            id = 1,
            startDate = LocalDate.of(2024, 12, 1),
            endDate = LocalDate.of(2024, 12, 10)
        )
        val upcomingTrip = createTrip(
            id = 2,
            startDate = LocalDate.of(2025, 2, 1),
            endDate = LocalDate.of(2025, 2, 10)
        )
        val ongoingTrip = createTrip(
            id = 3,
            startDate = LocalDate.of(2025, 1, 10),
            endDate = LocalDate.of(2025, 1, 20)
        )

        coEvery { repository.getTripList() } returns Result.success(
            listOf(pastTrip, upcomingTrip, ongoingTrip)
        )

        // When
        val result = useCase()

        // Then
        assertTrue(result.isSuccess)

        val groupedTrips = result.getOrNull()!!

        assertEquals(3, groupedTrips.size)
        assertEquals(1, groupedTrips[TripStatus.PAST]?.size)
        assertEquals(1, groupedTrips[TripStatus.UPCOMING]?.size)
        assertEquals(1, groupedTrips[TripStatus.ONGOING]?.size)
        assertEquals(TripStatus.PAST, groupedTrips[TripStatus.PAST]?.first()?.status)
        assertEquals(TripStatus.UPCOMING, groupedTrips[TripStatus.UPCOMING]?.first()?.status)
        assertEquals(TripStatus.ONGOING, groupedTrips[TripStatus.ONGOING]?.first()?.status)
    }

    @Test
    fun `invoke groups multiple trips with same status together`() = runTest {
        // Given
        val pastTrip1 = createTrip(
            id = 1,
            startDate = LocalDate.of(2024, 11, 1),
            endDate = LocalDate.of(2024, 11, 10)
        )
        val pastTrip2 = createTrip(
            id = 2,
            startDate = LocalDate.of(2024, 12, 1),
            endDate = LocalDate.of(2024, 12, 10)
        )
        val upcomingTrip = createTrip(
            id = 3,
            startDate = LocalDate.of(2025, 3, 1),
            endDate = LocalDate.of(2025, 3, 10)
        )

        coEvery { repository.getTripList() } returns Result.success(
            listOf(pastTrip1, pastTrip2, upcomingTrip)
        )

        // When
        val result = useCase()

        // Then
        assertTrue(result.isSuccess)

        val groupedTrips = result.getOrNull() ?: return@runTest

        assertEquals(2, groupedTrips.size)
        assertEquals(2, groupedTrips[TripStatus.PAST]?.size)
        assertEquals(1, groupedTrips[TripStatus.UPCOMING]?.size)
    }

    @Test
    fun `invoke returns failure when repository fails`() = runTest {
        // Given
        val exception = RuntimeException("Database error")
        coEvery { repository.getTripList() } returns Result.failure(exception)

        // When
        val result = useCase()

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        coVerify(exactly = 1) { repository.getTripList() }
    }

    @Test
    fun `invoke handles trips with UNSCHEDULED status and recalculates them`() = runTest {
        // Given
        val unscheduledTrip = createTrip(
            id = 1,
            startDate = LocalDate.of(2025, 6, 1),
            endDate = LocalDate.of(2025, 6, 10),
            status = TripStatus.UNSCHEDULED
        )

        coEvery { repository.getTripList() } returns Result.success(listOf(unscheduledTrip))

        // When
        val result = useCase()

        // Then
        assertTrue(result.isSuccess)

        val groupedTrips = result.getOrNull()!!

        assertEquals(1, groupedTrips.size)
        assertEquals(TripStatus.UPCOMING, groupedTrips[TripStatus.UPCOMING]?.first()?.status)
    }

    @Test
    fun `invoke preserves all trip properties during transformation`() = runTest {
        // Given
        val originalTrip = createTrip(
            id = 123,
            title = "Test Trip",
            location = "Test Location",
            startDate = LocalDate.of(2025, 6, 1),
            endDate = LocalDate.of(2025, 6, 10),
            imageUrl = "https://example.com/image.jpg"
        )

        coEvery { repository.getTripList() } returns Result.success(listOf(originalTrip))

        // When
        val result = useCase()

        // Then
        assertTrue(result.isSuccess)

        val trip = result.getOrNull()?.values?.first()?.first() ?: return@runTest

        assertEquals(123, trip.id)
        assertEquals("Test Trip", trip.title)
        assertEquals("Test Location", trip.location)
        assertEquals(LocalDate.of(2025, 6, 1), trip.startDate)
        assertEquals(LocalDate.of(2025, 6, 10), trip.endDate)
        assertEquals("https://example.com/image.jpg", trip.imageUrl)
    }

    private fun createTrip(
        id: Int = 1,
        title: String = "Test Trip",
        location: String = "Test Location",
        startDate: LocalDate,
        endDate: LocalDate,
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
