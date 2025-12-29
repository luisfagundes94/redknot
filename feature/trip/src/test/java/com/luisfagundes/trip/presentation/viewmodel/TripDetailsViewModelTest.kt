package com.luisfagundes.trip.presentation.viewmodel

import com.luisfagundes.core.testing.MainDispatcherRule
import com.luisfagundes.trip.domain.usecase.GetTripByIdUseCase
import com.luisfagundes.trip.presentation.fixtures.fakeTrip
import com.luisfagundes.trip.presentation.state.TripDetailsUiState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.Rule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class TripDetailsViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule(UnconfinedTestDispatcher())

    private val getTripByIdUseCase: GetTripByIdUseCase = mockk()

    private lateinit var viewModel: TripDetailsViewModel

    @BeforeEach
    fun setUp() {
        viewModel = createViewModel()
    }

    @Test
    fun `initial state is loading`() {
        // Then
        val currentState = viewModel.uiState.value
        val expectedState = TripDetailsUiState.Loading

        assertEquals(currentState, expectedState)
    }

    @Test
    fun `getTripById with valid id returns success state`() = runTest {
        // Given
        val tripId = 1
        val expectedTrip = fakeTrip
        coEvery { getTripByIdUseCase.invoke(tripId) } returns Result.success(expectedTrip)

        // When
        viewModel.getTripById(tripId)

        // Then
        val currentState = viewModel.uiState.value
        val expectedState = TripDetailsUiState.Success(expectedTrip)

        assertEquals(currentState, expectedState)
    }

    @Test
    fun `getTripById with failure returns error state`() = runTest {
        // Given
        val tripId = 1
        val errorMessage = "Trip not found"
        val exception = Exception(errorMessage)
        coEvery { getTripByIdUseCase.invoke(tripId) } returns Result.failure(exception)

        // When
        viewModel.getTripById(tripId)

        // Then
        val currentState = viewModel.uiState.value
        val expectedState = TripDetailsUiState.Error(errorMessage)

        assertEquals(currentState, expectedState)
    }

    @Test
    fun `getTripById sets Loading state before fetching`() = runTest {
        // Given
        val tripId = 1
        val states = mutableListOf<TripDetailsUiState>()
        coEvery { getTripByIdUseCase.invoke(tripId) } returns Result.success(fakeTrip)

        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.toList(states)
        }

        // When
        viewModel.getTripById(tripId)

        // Then
        assertTrue(states.contains(TripDetailsUiState.Loading))

        job.cancel()
    }

    private fun createViewModel() = TripDetailsViewModel(
        getTripByIdUseCase = getTripByIdUseCase,
        dispatcher = dispatcherRule.testDispatcher
    )
}