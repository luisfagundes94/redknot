package com.luisfagundes.trip.presentation.viewmodel

import com.luisfagundes.core.testing.MainDispatcherRule
import com.luisfagundes.trip.domain.model.TripStatus
import com.luisfagundes.trip.domain.usecase.GetTripListUseCase
import com.luisfagundes.trip.presentation.fixtures.fakePastTrip
import com.luisfagundes.trip.presentation.fixtures.fakeUpcomingTrip
import com.luisfagundes.trip.presentation.viewmodel.state.TripListUiState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class TripListViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule(UnconfinedTestDispatcher())

    private val getTripListUseCase: GetTripListUseCase = mockk()

    private lateinit var viewModel: TripListViewModel

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
        val tripsByStatus = mapOf(
            TripStatus.UPCOMING to listOf(fakeUpcomingTrip),
            TripStatus.PAST to listOf(fakePastTrip)
        )
        coEvery { getTripListUseCase.invoke() } returns Result.success(tripsByStatus)

        viewModel = createViewModel()

        // When
        viewModel.getTripList()

        // Then
        val currentState = viewModel.uiState.value
        val expectedState = TripListUiState.Success(tripsByStatus)

        assertEquals(currentState, expectedState)
    }

    @Test
    fun `getTripList with empty list returns empty state`() = runTest {
        // Given
        coEvery { getTripListUseCase.invoke() } returns Result.success(emptyMap())

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
