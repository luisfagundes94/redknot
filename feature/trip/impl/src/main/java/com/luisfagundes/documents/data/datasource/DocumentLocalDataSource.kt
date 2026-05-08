package com.luisfagundes.documents.data.datasource

import com.luisfagundes.documents.data.model.DocumentEntity

internal interface DocumentLocalDataSource {
    suspend fun saveDocument(entity: DocumentEntity): Result<Unit>
    suspend fun getDocumentsByTripId(tripId: Int): Result<List<DocumentEntity>>
}
