package com.luisfagundes.itinerary.presentation.viewmodel

import app.cash.turbine.test
import com.luisfagundes.itinerary.domain.model.Activity
import com.luisfagundes.itinerary.domain.usecase.GetItineraryItemsByDayUseCase
import com.luisfagundes.itinerary.presentation.viewmodel.effect.ItineraryUiEffect
import com.luisfagundes.itinerary.presentation.viewmodel.state.ItineraryUiState
import com.luisfagundes.core.testing.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalCoroutinesApi::class)
internal class ItineraryViewModelTest {

    @RegisterExtension
    val dispatcher = MainDispatcherRule(UnconfinedTestDispatcher())

    private val getItineraryItemsByDayUseCase: GetItineraryItemsByDayUseCase = mockk()

    private val viewModel = ItineraryViewModel(
        getItineraryItemsByDayUseCase = getItineraryItemsByDayUseCase,
        dispatcher = dispatcher.testDispatcher
    )

    @Test
    fun `initial state is Loading`() = runTest {
        // Then
        viewModel.uiState.test {
            assertEquals(ItineraryUiState.Loading, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getItineraryItemsByDay with non-empty list emits Content state`() = runTest {
        // Given
        val tripId = 1
        val itemsByDay = mapOf(fakeDay to listOf(fakeActivity))

        coEvery { getItineraryItemsByDayUseCase(tripId) } returns Result.success(itemsByDay)

        viewModel.uiState.test {
            awaitItem() // consume Loading

            // When
            viewModel.getItineraryItemsByDay(tripId)

            // Then
            assertEquals(ItineraryUiState.Content(itemsByDay), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getItineraryItemsByDay with empty list emits Empty state`() = runTest {
        // Given
        val tripId = 1

        coEvery { getItineraryItemsByDayUseCase(tripId) } returns Result.success(emptyMap())

        viewModel.uiState.test {
            awaitItem() // consume Loading

            // When
            viewModel.getItineraryItemsByDay(tripId)

            // Then
            assertEquals(ItineraryUiState.Empty, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getItineraryItemsByDay with failure does not update state`() = runTest {
        // Given
        val tripId = 1
        val error = Exception("DB error")

        coEvery { getItineraryItemsByDayUseCase(tripId) } returns Result.failure(error)

        viewModel.uiState.test {
            awaitItem() // consume Loading

            // When
            viewModel.getItineraryItemsByDay(tripId)

            // Then
            expectNoEvents()
        }
    }

    @Test
    fun `navigateToAddItineraryItem sends NavigateToItineraryItemForm effect`() = runTest {
        viewModel.uiEffect.test {
            // When
            viewModel.navigateToAddItineraryItem()

            // Then
            assertEquals(ItineraryUiEffect.NavigateToItineraryItemForm, awaitItem())
        }
    }
}

private val fakeDay = LocalDate.of(2025, 6, 10)

private val fakeActivity = Activity(
    id = "1",
    tripId = 1,
    date = fakeDay,
    time = LocalTime.of(10, 0),
    title = "City Tour",
    description = null,
    location = null,
    imageUrl = null
)
