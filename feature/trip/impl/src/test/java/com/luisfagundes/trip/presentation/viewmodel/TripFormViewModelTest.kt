package com.luisfagundes.trip.presentation.viewmodel

import app.cash.turbine.test
import com.luisfagundes.common.domain.model.CommonFieldError
import com.luisfagundes.common.domain.model.DateFieldError
import com.luisfagundes.common.domain.model.FieldValidationResult
import com.luisfagundes.common.domain.usecase.GetUnsplashImageUseCase
import com.luisfagundes.common.domain.usecase.ValidateDateUseCase
import com.luisfagundes.common.domain.usecase.ValidateTitleUseCase
import com.luisfagundes.core.testing.MainDispatcherRule
import com.luisfagundes.trip.domain.model.TripStatus
import com.luisfagundes.trip.domain.usecase.CreateTripUseCase
import com.luisfagundes.trip.domain.usecase.ValidateDestinationUseCase
import com.luisfagundes.trip.presentation.viewmodel.effect.TripFormUiEffect
import com.luisfagundes.trip.presentation.viewmodel.state.TripFormUiState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
internal class TripFormViewModelTest {

    @RegisterExtension
    val dispatcherRule = MainDispatcherRule(UnconfinedTestDispatcher())

    private val validateTitleUseCase: ValidateTitleUseCase = mockk()
    private val validateDateUseCase: ValidateDateUseCase = mockk()
    private val validateDestinationUseCase: ValidateDestinationUseCase = mockk()
    private val getUnsplashImageUseCase: GetUnsplashImageUseCase = mockk()
    private val createTripUseCase: CreateTripUseCase = mockk()

    private val viewModel = TripFormViewModel(
        validateTitleUseCase = validateTitleUseCase,
        validateDateUseCase = validateDateUseCase,
        validateDestinationUseCase = validateDestinationUseCase,
        getUnsplashImageUseCase = getUnsplashImageUseCase,
        createTripUseCase = createTripUseCase,
        dispatcher = dispatcherRule.testDispatcher
    )

    @Test
    fun `initial state has default values`() = runTest {
        viewModel.uiState.test {
            assertEquals(TripFormUiState(), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `updateTitle updates title and clears error when valid`() = runTest {
        // Given
        every { validateTitleUseCase(any()) } returns FieldValidationResult.Valid

        viewModel.uiState.test {
            awaitItem() // consume initial state

            // When
            viewModel.updateTitle("Paris Trip")

            // Then
            val currentState = awaitItem()
            assertEquals("Paris Trip", currentState.title)
            assertNull(currentState.titleError)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `updateTitle updates title and sets error when invalid`() = runTest {
        // Given
        val error = CommonFieldError.EMPTY
        every { validateTitleUseCase(any()) } returns FieldValidationResult.Invalid(error)

        viewModel.uiState.test {
            awaitItem() // consume initial state

            // When
            viewModel.updateTitle("")

            // Then
            val currentState = awaitItem()
            assertEquals("", currentState.title)
            assertEquals(error, currentState.titleError)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `updateStartDate updates startDate and clears error when valid`() = runTest {
        // Given
        val date = LocalDate.of(2025, 6, 15)
        every { validateDateUseCase(any()) } returns FieldValidationResult.Valid

        viewModel.uiState.test {
            awaitItem() // consume initial state

            // When
            viewModel.updateStartDate(date)

            // Then
            val currentState = awaitItem()
            assertEquals(date, currentState.startDate)
            assertNull(currentState.startDateError)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `updateStartDate sets error when date is null`() = runTest {
        // Given
        val error = DateFieldError.MISSING
        every { validateDateUseCase(null) } returns FieldValidationResult.Invalid(error)

        viewModel.uiState.test {
            awaitItem() // consume initial state

            // When
            viewModel.updateStartDate(null)

            // Then
            val currentState = awaitItem()
            assertNull(currentState.startDate)
            assertEquals(error, currentState.startDateError)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `updateEndDate updates endDate and clears error when valid`() = runTest {
        // Given
        val date = LocalDate.of(2025, 6, 20)
        every { validateDateUseCase(any()) } returns FieldValidationResult.Valid

        viewModel.uiState.test {
            awaitItem() // consume initial state

            // When
            viewModel.updateEndDate(date)

            // Then
            val currentState = awaitItem()
            assertEquals(date, currentState.endDate)
            assertNull(currentState.endDateError)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `updateEndDate sets error when date is null`() = runTest {
        // Given
        val error = DateFieldError.MISSING
        every { validateDateUseCase(null) } returns FieldValidationResult.Invalid(error)

        viewModel.uiState.test {
            awaitItem() // consume initial state

            // When
            viewModel.updateEndDate(null)

            // Then
            val currentState = awaitItem()
            assertNull(currentState.endDate)
            assertEquals(error, currentState.endDateError)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `updateDestination updates destination and clears error when valid`() = runTest {
        // Given
        every { validateDestinationUseCase(any()) } returns FieldValidationResult.Valid

        viewModel.uiState.test {
            awaitItem() // consume initial state

            // When
            viewModel.updateDestination("Paris, France")

            // Then
            val currentState = awaitItem()
            assertEquals("Paris, France", currentState.destination)
            assertNull(currentState.destinationError)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `updateDestination sets error when invalid`() = runTest {
        // Given
        val error = CommonFieldError.EMPTY
        every { validateDestinationUseCase(any()) } returns FieldValidationResult.Invalid(error)

        viewModel.uiState.test {
            awaitItem() // consume initial state

            // When
            viewModel.updateDestination("")

            // Then
            val currentState = awaitItem()
            assertEquals("", currentState.destination)
            assertEquals(error, currentState.destinationError)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `submit sets isLoading to true while processing`() = runTest {
        // Given
        coEvery { getUnsplashImageUseCase(any()) } coAnswers {
            delay(100)
            Result.success("https://example.com/image.jpg")
        }
        coEvery { createTripUseCase(any()) } returns Result.success(Unit)

        viewModel.uiState.test {
            awaitItem() // consume initial state

            // When
            viewModel.submit()

            // Then
            assertTrue(awaitItem().isLoading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `submit with successful creation sends NavigateBack effect`() = runTest {
        // Given
        val imageUrl = "https://example.com/image.jpg"
        coEvery { getUnsplashImageUseCase(any()) } returns Result.success(imageUrl)
        coEvery { createTripUseCase(any()) } returns Result.success(Unit)

        viewModel.uiEffect.test {
            // When
            viewModel.submit()

            // Then
            assertEquals(TripFormUiEffect.NavigateBack, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `submit with failed creation sends ShowErrorToast effect`() = runTest {
        // Given
        val exception = Exception("Creation failed")
        coEvery { getUnsplashImageUseCase(any()) } returns Result.success("")
        coEvery { createTripUseCase(any()) } returns Result.failure(exception)

        viewModel.uiEffect.test {
            // When
            viewModel.submit()

            // Then
            assertTrue(awaitItem() is TripFormUiEffect.ShowErrorToast)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `submit with failed image fetch continues with empty imageUrl`() = runTest {
        // Given
        coEvery { getUnsplashImageUseCase(any()) } returns Result.failure(Exception("Image fetch failed"))
        coEvery { createTripUseCase(any()) } returns Result.success(Unit)

        viewModel.uiEffect.test {
            // When
            viewModel.submit()

            // Then
            coVerify { createTripUseCase(match { it.imageUrl.isEmpty() }) }
            assertEquals(TripFormUiEffect.NavigateBack, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `submit creates trip with correct data from state`() = runTest {
        // Given
        val imageUrl = "https://example.com/paris.jpg"
        val startDate = LocalDate.of(2025, 6, 15)
        val endDate = LocalDate.of(2025, 6, 20)

        every { validateTitleUseCase(any()) } returns FieldValidationResult.Valid
        every { validateDateUseCase(any()) } returns FieldValidationResult.Valid
        every { validateDestinationUseCase(any()) } returns FieldValidationResult.Valid
        coEvery { getUnsplashImageUseCase("Paris") } returns Result.success(imageUrl)
        coEvery { createTripUseCase(any()) } returns Result.success(Unit)

        viewModel.updateTitle("Paris Trip")
        viewModel.updateStartDate(startDate)
        viewModel.updateEndDate(endDate)
        viewModel.updateDestination("Paris")

        viewModel.uiEffect.test {
            // When
            viewModel.submit()

            // Then
            coVerify {
                createTripUseCase(
                    match { trip ->
                        trip.title == "Paris Trip" &&
                            trip.location == "Paris" &&
                            trip.startDate == startDate &&
                            trip.endDate == endDate &&
                            trip.imageUrl == imageUrl &&
                            trip.status == TripStatus.UNSCHEDULED
                    }
                )
            }
            awaitItem() // NavigateBack
            cancelAndIgnoreRemainingEvents()
        }
    }
}
