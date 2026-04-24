package com.luisfagundes.trip.presentation.viewmodel

import app.cash.turbine.test
import com.luisfagundes.core.testing.MainDispatcherRule
import com.luisfagundes.trip.domain.model.TripStatus
import com.luisfagundes.trip.domain.usecase.GetTripListUseCase
import com.luisfagundes.trip.presentation.fixtures.fakePastTrip
import com.luisfagundes.trip.presentation.fixtures.fakeUpcomingTrip
import com.luisfagundes.trip.presentation.viewmodel.effect.TripListUiEffect
import com.luisfagundes.trip.presentation.viewmodel.state.TripListUiState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@OptIn(ExperimentalCoroutinesApi::class)
internal class TripListViewModelTest {
    @RegisterExtension
    val dispatcher = MainDispatcherRule(UnconfinedTestDispatcher())

    private val getTripListUseCase: GetTripListUseCase = mockk()

    private val viewModel = TripListViewModel(
        getTripListUseCase = getTripListUseCase,
        dispatcher = dispatcher.testDispatcher
    )

    @Test
    fun `initial state is Loading`() = runTest {
        // Then
        viewModel.uiState.test {
            assertEquals(TripListUiState.Loading, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getTripList with sections returns content state`() = runTest {
        // Given
        val tripsByStatus = mapOf(
            TripStatus.UPCOMING to listOf(fakeUpcomingTrip),
            TripStatus.PAST to listOf(fakePastTrip)
        )

        coEvery { getTripListUseCase.invoke() } returns Result.success(tripsByStatus)

        viewModel.uiState.test {
            awaitItem() // Consume initial loading

            // When
            viewModel.getTripList()

            // Then
            assertEquals(TripListUiState.Success(tripsByStatus), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getTripList with empty list returns empty state`() = runTest {
        // Given
        coEvery { getTripListUseCase.invoke() } returns Result.success(emptyMap())

        viewModel.uiState.test {
            awaitItem() // Consume initial loading

            // When
            viewModel.getTripList()

            // Then
            assertEquals(TripListUiState.Empty, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getTripList with failure returns Error state`() = runTest {
        // Given
        val exception = Exception("Network error")
        coEvery { getTripListUseCase.invoke() } returns Result.failure(exception)

        viewModel.uiState.test {
            awaitItem() // Consume initial loading

            // When
            viewModel.getTripList()

            // Then
            assertEquals(TripListUiState.Error, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onTripClick event should send NavigateToTripForm effect`() = runTest {
        // Given
        val id = 123

        viewModel.uiEffect.test {
            // When
            viewModel.onTripClick(id)

            // Then
            assertEquals(TripListUiEffect.NavigateToTripDetails(id), awaitItem())
        }
    }

    @Test
    fun `onCreateTripClick should send NavigateToTripForm effect`() = runTest {
        viewModel.uiEffect.test {
            // When
            viewModel.onCreateTripClick()

            // Then
            assertEquals(TripListUiEffect.NavigateToTripForm, awaitItem())
        }
    }
}
