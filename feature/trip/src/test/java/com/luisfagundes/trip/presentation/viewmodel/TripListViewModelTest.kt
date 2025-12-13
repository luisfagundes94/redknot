package com.luisfagundes.trip.presentation.viewmodel

import com.luisfagundes.core.testing.MainDispatcherRule
import com.luisfagundes.trip.domain.model.TripState
import com.luisfagundes.trip.domain.usecase.GetTripListUseCase
import com.luisfagundes.trip.presentation.state.TripListUiState
import com.luisfagundes.trip.presentation.stubs.fakeTrip
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class TripListViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule(UnconfinedTestDispatcher())

    private lateinit var getTripListUseCase: GetTripListUseCase
    private lateinit var viewModel: TripListViewModel

    @BeforeEach
    fun setup() {
        getTripListUseCase = mockk()
    }

    @Test
    fun `initial state is Loading`() {
        // Given & When
        viewModel = createViewModel()

        // Then
        val currentState = viewModel.uiState.value
        val expectedState = TripListUiState.Loading
        assertEquals(currentState, expectedState)
    }

    @Test
    fun `getTripList with sections returns content state`() = runTest {
        // Given
        val upcomingTrip = fakeTrip.copy(id = 1, title = "Paris Trip", state = TripState.UPCOMING)
        val pastTrip = fakeTrip.copy(id = 2, title = "Rome Trip", state = TripState.PAST)
        val trips = listOf(upcomingTrip, pastTrip)

        coEvery { getTripListUseCase.invoke() } returns Result.success(trips)
        viewModel = createViewModel()

        // When
        viewModel.getTripList()

        // Then
        val currentState = viewModel.uiState.value
        val expectedState = TripListUiState.Success(listOf(upcomingTrip), listOf(pastTrip))
        assertEquals(currentState, expectedState)
    }

    @Test
    fun `getTripList with empty list returns empty state`() = runTest {
        // Given
        coEvery { getTripListUseCase.invoke() } returns Result.success(emptyList())
        viewModel = createViewModel()

        // When
        viewModel.getTripList()

        // Then
        val currentState = viewModel.uiState.value
        val expectedState = TripListUiState.Empty
        assertEquals(currentState, expectedState)
    }

    @Test
    fun `getTripList with failure returns Error state`() = runTest {
        // Given
        val exception = Exception("Network error")
        coEvery { getTripListUseCase.invoke() } returns Result.failure(exception)
        viewModel = createViewModel()

        // When
        viewModel.getTripList()

        // Then
        val currentState = viewModel.uiState.value
        val expectedState = TripListUiState.Error
        assertEquals(currentState, expectedState)
    }

    private fun createViewModel() = TripListViewModel(
        getTripListUseCase = getTripListUseCase,
        dispatcher = dispatcherRule.testDispatcher
    )
}
