package com.luisfagundes.itinerary.presentation.viewmodel

import app.cash.turbine.test
import com.luisfagundes.core.testing.MainDispatcherRule
import com.luisfagundes.itinerary.domain.model.Activity
import com.luisfagundes.itinerary.domain.model.ItineraryItem
import com.luisfagundes.itinerary.domain.usecase.GetItineraryItemListUseCase
import com.luisfagundes.itinerary.presentation.viewmodel.effect.ItineraryUiEffect
import com.luisfagundes.itinerary.presentation.viewmodel.state.ItineraryUiState
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

    private val getItineraryItemListUseCase: GetItineraryItemListUseCase = mockk()

    private val viewModel = ItineraryViewModel(
        getItineraryItemListUseCase = getItineraryItemListUseCase,
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
    fun `OnGetItineraryList with non-empty list emits Content state`() = runTest {
        // Given
        val tripId = 1
        val items: List<ItineraryItem> = listOf(fakeActivity)

        coEvery { getItineraryItemListUseCase(tripId) } returns Result.success(items)

        viewModel.uiState.test {
            awaitItem() // consume Loading

            // When
            viewModel.getItineraryList(tripId)

            // Then
            assertEquals(ItineraryUiState.Content(items), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `OnGetItineraryList with empty list emits Empty state`() = runTest {
        // Given
        val tripId = 1

        coEvery { getItineraryItemListUseCase(tripId) } returns Result.success(emptyList())

        viewModel.uiState.test {
            awaitItem() // consume Loading

            // When
            viewModel.getItineraryList(tripId)

            // Then
            assertEquals(ItineraryUiState.Empty, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `OnGetItineraryList with failure does not update state`() = runTest {
        // Given
        val tripId = 1
        val error = Exception("DB error")

        coEvery { getItineraryItemListUseCase(tripId) } returns Result.failure(error)

        viewModel.uiState.test {
            awaitItem() // consume Loading

            // When
            viewModel.getItineraryList(tripId)

            // Then
            expectNoEvents()
        }
    }

    @Test
    fun `OnNewItineraryItemClick sends NavigateToItineraryItemForm effect`() = runTest {
        viewModel.uiEffect.test {
            // When
            viewModel.onNewItineraryItemClick()

            // Then
            assertEquals(ItineraryUiEffect.NavigateToItineraryItemForm, awaitItem())
        }
    }
}

private val fakeActivity = Activity(
    id = "1",
    tripId = 1,
    date = LocalDate.of(2025, 6, 10),
    time = LocalTime.of(10, 0),
    title = "City Tour",
    description = null,
    location = null,
    imageUrl = null
)
