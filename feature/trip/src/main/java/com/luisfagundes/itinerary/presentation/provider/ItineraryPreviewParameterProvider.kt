package com.luisfagundes.itinerary.presentation.provider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.luisfagundes.itinerary.domain.model.Accommodation
import com.luisfagundes.itinerary.domain.model.Airport
import com.luisfagundes.itinerary.domain.model.CheckInType
import com.luisfagundes.itinerary.domain.model.Flight
import com.luisfagundes.itinerary.domain.model.MealType
import com.luisfagundes.itinerary.domain.model.Restaurant
import com.luisfagundes.itinerary.presentation.viewmodel.state.ItineraryUiState
import java.time.LocalDate
import java.time.LocalTime
import kotlin.time.Duration.Companion.hours

internal class ItineraryPreviewParameterProvider :
    PreviewParameterProvider<ItineraryUiState.Content> {
    private val items = listOf(
        Flight(
            id = "5425123",
            tripId = 123,
            date = LocalDate.of(2026, 10, 5),
            time = LocalTime.of(15, 0, 0),
            companyName = "Air France",
            flightNumber = "BA123",
            origin = Airport(
                name = "Galeão International Airport",
                city = "Rio de Janeiro"
            ),
            destination = Airport(
                name = "DXB",
                city = "Orlando"
            ),
            duration = 10.hours,
            seatNumber = "1234",
        ),
        Accommodation(
            id = "551514",
            tripId = 123,
            date = LocalDate.of(2026, 11, 5),
            time = LocalTime.of(10, 0, 0),
            name = "Florida Alligator Hotel",
            address = "1500 Sand Lake Road",
            checkInType = CheckInType.CHECK_IN,
            imageUrl = "https://cf.bstatic.com/xdata/images/hotel/max1024x768/277437040.jpg?k=777413f61aaf173f419daaf829a269a43564a8ae8f2d7fb6c14417728c2c27aa&o="
        ),
        Restaurant(
            id = "1222",
            tripId = 123,
            date = LocalDate.of(2026, 11, 5),
            time = LocalTime.of(16, 0, 0),
            name = "Mc Donald's",
            address = "5498 Central Florida Pkwy",
            mealType = MealType.LUNCH
        )
    )

    override val values = sequenceOf(
        ItineraryUiState.Content(
            itemsByDay = items.groupBy { it.date }
        )
    )
}