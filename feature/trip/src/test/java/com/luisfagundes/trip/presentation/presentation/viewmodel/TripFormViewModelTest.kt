package com.luisfagundes.trip.presentation.presentation.viewmodel

import com.luisfagundes.core.testing.MainDispatcherRule
import com.luisfagundes.trip.domain.model.TripStatus
import com.luisfagundes.trip.domain.model.ValidationError
import com.luisfagundes.trip.domain.model.ValidationResult
import com.luisfagundes.trip.domain.usecase.CreateTripUseCase
import com.luisfagundes.common.domain.usecase.GetUnsplashImageUseCase
import com.luisfagundes.trip.domain.usecase.ValidateDateUseCase
import com.luisfagundes.trip.domain.usecase.ValidateDestinationUseCase
import com.luisfagundes.trip.domain.usecase.ValidateTitleUseCase
import com.luisfagundes.trip.presentation.effect.TripFormUiEffect
import com.luisfagundes.trip.presentation.state.TripFormUiState
import com.luisfagundes.trip.presentation.viewmodel.TripFormViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
internal class TripFormViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule(UnconfinedTestDispatcher())

    private val validateTitleUseCase: ValidateTitleUseCase = mockk()
    private val validateDateUseCase: ValidateDateUseCase = mockk()
    private val validateDestinationUseCase: ValidateDestinationUseCase = mockk()
    private val getUnsplashImageUseCase: GetUnsplashImageUseCase = mockk()
    private val createTripUseCase: CreateTripUseCase = mockk()

    private lateinit var viewModel: TripFormViewModel

    @BeforeEach
    fun setup() {
        viewModel = createViewModel()
    }

    @Test
    fun `initial state has default values`() {
        // Then
        val currentState = viewModel.uiState.value
        val expectedState = TripFormUiState()

        assertEquals(expectedState, currentState)
    }

    @Test
    fun `onTitleChange updates title and clears error when valid`() {
        // Given
        every { validateTitleUseCase(any()) } returns ValidationResult.Valid

        // When
        viewModel.onTitleChange("Paris Trip")

        // Then
        val currentState = viewModel.uiState.value

        assertEquals("Paris Trip", currentState.title)
        assertNull(currentState.titleError)
    }

    @Test
    fun `onTitleChange updates title and sets error when invalid`() {
        // Given
        val error = ValidationError.EMPTY_TITLE
        every { validateTitleUseCase(any()) } returns ValidationResult.Invalid(error)

        // When
        viewModel.onTitleChange("")

        // Then
        val currentState = viewModel.uiState.value

        assertEquals("", currentState.title)
        assertEquals(error, currentState.titleError)
    }

    @Test
    fun `onStartDateChange updates startDate and clears error when valid`() {
        // Given
        val date = LocalDate.of(2025, 6, 15)
        every { validateDateUseCase(any()) } returns ValidationResult.Valid

        // When
        viewModel.onStartDateChange(date)

        // Then
        val currentState = viewModel.uiState.value

        assertEquals(date, currentState.startDate)
        assertNull(currentState.startDateError)
    }

    @Test
    fun `onStartDateChange sets error when date is null`() {
        // Given
        val error = ValidationError.MISSING_DATE
        every { validateDateUseCase(null) } returns ValidationResult.Invalid(error)

        // When
        viewModel.onStartDateChange(null)

        // Then
        val currentState = viewModel.uiState.value

        assertNull(currentState.startDate)
        assertEquals(error, currentState.startDateError)
    }

    @Test
    fun `onEndDateChange updates endDate and clears error when valid`() {
        // Given
        val date = LocalDate.of(2025, 6, 20)
        every { validateDateUseCase(any()) } returns ValidationResult.Valid

        // When
        viewModel.onEndDateChange(date)

        // Then
        val currentState = viewModel.uiState.value

        assertEquals(date, currentState.endDate)
        assertNull(currentState.endDateError)
    }

    @Test
    fun `onEndDateChange sets error when date is null`() {
        // Given
        val error = ValidationError.MISSING_DATE
        every { validateDateUseCase(null) } returns ValidationResult.Invalid(error)

        // When
        viewModel.onEndDateChange(null)

        // Then
        val currentState = viewModel.uiState.value

        assertNull(currentState.endDate)
        assertEquals(error, currentState.endDateError)
    }

    @Test
    fun `onDestinationChange updates destination and clears error when valid`() {
        // Given
        every { validateDestinationUseCase(any()) } returns ValidationResult.Valid

        // When
        viewModel.onDestinationChange("Paris, France")

        // Then
        val currentState = viewModel.uiState.value

        assertEquals("Paris, France", currentState.destination)
        assertNull(currentState.destinationError)
    }

    @Test
    fun `onDestinationChange sets error when invalid`() {
        // Given
        val error = ValidationError.EMPTY_DESTINATION
        every { validateDestinationUseCase(any()) } returns ValidationResult.Invalid(error)

        // When
        viewModel.onDestinationChange("")

        // Then
        val currentState = viewModel.uiState.value

        assertEquals("", currentState.destination)
        assertEquals(error, currentState.destinationError)
    }

    @Test
    fun `onSubmit sets isLoading to true while processing`() = runTest {
        // Given
        coEvery { getUnsplashImageUseCase(any()) } coAnswers {
            delay(100)
            Result.success("https://example.com/image.jpg")
        }
        coEvery { createTripUseCase(any()) } returns Result.success(Unit)

        // When
        viewModel.onSubmit()

        // Then
        assertTrue(viewModel.uiState.value.isLoading)
    }

    @Test
    fun `onSubmit with successful creation sends NavigateBack effect`() = runTest {
        // Given
        val imageUrl = "https://example.com/image.jpg"

        coEvery { getUnsplashImageUseCase(any()) } returns Result.success(imageUrl)
        coEvery { createTripUseCase(any()) } returns Result.success(Unit)

        // When
        viewModel.onSubmit()

        // Then
        val effect = viewModel.uiEffect.first()

        assertEquals(TripFormUiEffect.NavigateBack, effect)
        assertFalse(viewModel.uiState.value.isLoading)
    }

    @Test
    fun `onSubmit with failed creation sends ShowErrorToast effect`() = runTest {
        // Given
        val exception = Exception("Creation failed")

        coEvery { getUnsplashImageUseCase(any()) } returns Result.success("")
        coEvery { createTripUseCase(any()) } returns Result.failure(exception)

        // When
        viewModel.onSubmit()

        // Then
        val effect = viewModel.uiEffect.first()

        assertTrue(effect is TripFormUiEffect.ShowErrorToast)
        assertFalse(viewModel.uiState.value.isLoading)
    }

    @Test
    fun `onSubmit with failed image fetch continues with empty imageUrl`() = runTest {
        // Given
        coEvery { getUnsplashImageUseCase(any()) } returns Result.failure(Exception("Image fetch failed"))
        coEvery { createTripUseCase(any()) } returns Result.success(Unit)

        // When
        viewModel.onSubmit()

        // Then
        coVerify { createTripUseCase(match { it.imageUrl.isEmpty() }) }

        val effect = viewModel.uiEffect.first()
        assertEquals(TripFormUiEffect.NavigateBack, effect)
    }

    @Test
    fun `onSubmit creates trip with correct data from state`() = runTest {
        // Given
        val imageUrl = "https://example.com/paris.jpg"
        val startDate = LocalDate.of(2025, 6, 15)
        val endDate = LocalDate.of(2025, 6, 20)

        every { validateTitleUseCase(any()) } returns ValidationResult.Valid
        every { validateDateUseCase(any()) } returns ValidationResult.Valid
        every { validateDestinationUseCase(any()) } returns ValidationResult.Valid
        coEvery { getUnsplashImageUseCase("Paris") } returns Result.success(imageUrl)
        coEvery { createTripUseCase(any()) } returns Result.success(Unit)

        viewModel.onTitleChange("Paris Trip")
        viewModel.onStartDateChange(startDate)
        viewModel.onEndDateChange(endDate)
        viewModel.onDestinationChange("Paris")

        // When
        viewModel.onSubmit()

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
    }

    private fun createViewModel() = TripFormViewModel(
        validateTitleUseCase = validateTitleUseCase,
        validateDateUseCase = validateDateUseCase,
        validateDestinationUseCase = validateDestinationUseCase,
        getUnsplashImageUseCase = getUnsplashImageUseCase,
        createTripUseCase = createTripUseCase,
        dispatcher = dispatcherRule.testDispatcher
    )
}