package com.luisfagundes.trip.data.repository

import com.luisfagundes.trip.domain.model.Trip
import com.luisfagundes.trip.domain.repository.TripRepository
import java.time.LocalDate
import java.time.Month
import javax.inject.Inject

internal class TripRepositoryImpl @Inject constructor() : TripRepository {
    override suspend fun getTripList(): Result<List<Trip>> {
        return runCatching {
            //emptyList()
            listOf(
                Trip(
                    id = 2,
                    imageUrl = "https://images.pexels.com/photos/2422461/pexels-photo-2422461.jpeg",
                    title = "Summer in Italy",
                    startDate = LocalDate.of(2026, Month.AUGUST, 15),
                    endDate = LocalDate.of(2026, Month.AUGUST, 25),
                    location = "Florence, Italy",
                    done = false
                ),
                Trip(
                    id = 3,
                    imageUrl = "https://images.pexels.com/photos/2082103/pexels-photo-2082103.jpeg",
                    title = "Weekend in Paris",
                    startDate = LocalDate.of(2026, Month.SEPTEMBER, 20),
                    endDate = LocalDate.of(2026, Month.SEPTEMBER, 24),
                    location = "Paris, France",
                    done = false
                ),
                Trip(
                    id = 1,
                    imageUrl = "https://images.pexels.com/photos/3411135/pexels-photo-3411135.jpeg",
                    title = "Disney Tour",
                    startDate = LocalDate.of(2022, Month.APRIL, 2),
                    endDate = LocalDate.of(2022, Month.APRIL, 24),
                    location = "Orlando, United States",
                    done = true
                )
            )
        }
    }
}