package com.luisfagundes.trip.presentation.viewmodel

import app.cash.turbine.test
import com.luisfagundes.core.testing.MainDispatcherRule
import com.luisfagundes.trip.domain.usecase.GetTripByIdUseCase
import com.luisfagundes.trip.presentation.fixtures.fakeTrip
import com.luisfagundes.trip.presentation.viewmodel.state.TripDetailsUiState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@OptIn(ExperimentalCoroutinesApi::class)
internal class TripDetailsViewModelTest {
    @RegisterExtension
    val dispatcher = MainDispatcherRule()

    private val getTripByIdUseCase: GetTripByIdUseCase = mockk()

    private val viewModel = TripDetailsViewModel(
        getTripByIdUseCase = getTripByIdUseCase,
        dispatcher = dispatcher.testDispatcher
    )

    @Test
    fun `initial state is loading`() = runTest {
        // Then
        viewModel.uiState.test {
            assertEquals(TripDetailsUiState.Loading, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getTripById with valid id returns success state`() = runTest {
        // Given
        val tripId = 1

        coEvery { getTripByIdUseCase.invoke(tripId) } returns Result.success(fakeTrip)

        viewModel.uiState.test {
            awaitItem() // consume initial Loading

            // When
            viewModel.getTripById(tripId)

            // Then
            assertEquals(TripDetailsUiState.Success(fakeTrip), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getTripById with failure returns error state`() = runTest {
        // Given
        val tripId = 1
        val errorMessage = "Trip not found"

        coEvery { getTripByIdUseCase.invoke(tripId) } returns Result.failure(Exception(errorMessage))

        viewModel.uiState.test {
            awaitItem() // consume initial Loading

            // When
            viewModel.getTripById(tripId)

            // Then
            assertEquals(TripDetailsUiState.Error(errorMessage), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getTripById sets Loading state before fetching`() = runTest {
        // Given
        val tripId = 1

        coEvery { getTripByIdUseCase.invoke(tripId) } returns Result.success(fakeTrip)

        viewModel.uiState.test {
            // Then (Loading is confirmed before fetch)
            assertEquals(TripDetailsUiState.Loading, awaitItem())

            // When
            viewModel.getTripById(tripId)

            // Then
            assertEquals(TripDetailsUiState.Success(fakeTrip), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
