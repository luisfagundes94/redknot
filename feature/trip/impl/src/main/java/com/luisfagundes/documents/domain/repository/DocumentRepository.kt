package com.luisfagundes.documents.domain.repository

import com.luisfagundes.documents.domain.model.Document

internal interface DocumentRepository {
    suspend fun saveDocument(document: Document): Result<Unit>
    suspend fun getDocumentsByTripId(tripId: Int): Result<List<Document>>
    suspend fun deleteDocument(document: Document): Result<Unit>
}
