package com.luisfagundes.trip.data.repository

import com.luisfagundes.trip.domain.model.Trip
import com.luisfagundes.trip.domain.repository.TripRepository
import javax.inject.Inject

internal class TripRepositoryImpl @Inject constructor() : TripRepository {
    override suspend fun getTripList(): Result<List<Trip>> {
        return runCatching {
            emptyList()
//            listOf(
//                Trip(
//                    id = 2,
//                    imageUrl = "https://images.pexels.com/photos/2422461/pexels-photo-2422461.jpeg",
//                    title = "Summer in Italy",
//                    period = "Aug 15 - Aug 25, 2026",
//                    location = "Florence, Italy",
//                    done = false
//                ),
//                Trip(
//                    id = 3,
//                    imageUrl = "https://images.pexels.com/photos/2082103/pexels-photo-2082103.jpeg",
//                    title = "Weekend in Paris",
//                    period = "Sep 20 - Sep 24, 2026",
//                    location = "Paris, France",
//                    done = false
//                ),
//                Trip(
//                    id = 1,
//                    imageUrl = "https://images.pexels.com/photos/3411135/pexels-photo-3411135.jpeg",
//                    title = "Disney Tour",
//                    period = "Apr 2 - Apr 24, 2022",
//                    location = "Orlando, United States",
//                    done = true
//                )
//            )
        }
    }
}