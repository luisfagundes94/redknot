package com.luisfagundes.documents.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.luisfagundes.trip.data.model.TripEntity

@Entity(
    tableName = "documents",
    foreignKeys = [
        ForeignKey(
            entity = TripEntity::class,
            parentColumns = ["id"],
            childColumns = ["trip_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("trip_id")]
)
internal data class DocumentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("trip_id") val tripId: Int,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("description") val description: String,
    @ColumnInfo("category") val category: String,
    @ColumnInfo("attachment_uri") val attachmentUri: String,
    @ColumnInfo("attachment_source") val attachmentSource: String,
    @ColumnInfo("attachment_file_name") val attachmentFileName: String?,
    @ColumnInfo("attachment_size_bytes") val attachmentSizeBytes: Long?,
    @ColumnInfo("attachment_mime_type") val attachmentMimeType: String?
)
