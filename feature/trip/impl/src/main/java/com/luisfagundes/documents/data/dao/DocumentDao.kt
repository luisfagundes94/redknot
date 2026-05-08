package com.luisfagundes.documents.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.luisfagundes.documents.data.model.DocumentEntity

@Dao
internal interface DocumentDao {
    @Insert
    suspend fun insert(document: DocumentEntity)

    @Query("SELECT * FROM documents WHERE trip_id = :tripId")
    suspend fun getByTripId(tripId: Int): List<DocumentEntity>

    @Delete
    suspend fun delete(document: DocumentEntity)
}
