package com.luisfagundes.documents.data.datasource

import com.luisfagundes.documents.data.dao.DocumentDao
import com.luisfagundes.documents.data.model.DocumentEntity
import javax.inject.Inject

internal class DocumentLocalDataSourceImpl @Inject constructor(
    private val dao: DocumentDao
) : DocumentLocalDataSource {

    override suspend fun saveDocument(entity: DocumentEntity): Result<Unit> {
        return runCatching { dao.insert(entity) }
    }

    override suspend fun getDocumentsByTripId(tripId: Int): Result<List<DocumentEntity>> {
        return runCatching { dao.getByTripId(tripId) }
    }

    override suspend fun getDocumentById(id: Int): Result<DocumentEntity?> {
        return runCatching { dao.getById(id) }
    }

    override suspend fun deleteDocument(entity: DocumentEntity): Result<Unit> {
        return runCatching { dao.delete(entity) }
    }
}
