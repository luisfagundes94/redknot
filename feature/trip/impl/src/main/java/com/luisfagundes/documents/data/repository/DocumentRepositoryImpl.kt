package com.luisfagundes.documents.data.repository

import com.luisfagundes.documents.data.datasource.DocumentLocalDataSource
import com.luisfagundes.documents.data.mapper.DocumentMapper
import com.luisfagundes.documents.domain.model.Document
import com.luisfagundes.documents.domain.repository.DocumentRepository
import javax.inject.Inject

internal class DocumentRepositoryImpl @Inject constructor(
    private val dataSource: DocumentLocalDataSource,
    private val mapper: DocumentMapper
) : DocumentRepository {

    override suspend fun saveDocument(document: Document): Result<Unit> {
        return dataSource.saveDocument(mapper.mapToEntity(document))
    }

    override suspend fun getDocumentsByTripId(tripId: Int): Result<List<Document>> {
        return dataSource.getDocumentsByTripId(tripId).map { entities ->
            entities.map { mapper.mapToDomain(it) }
        }
    }

    override suspend fun getDocumentById(id: Int): Result<Document> {
        return dataSource.getDocumentById(id).mapCatching { entity ->
            entity?.let { mapper.mapToDomain(it) }
                ?: throw NoSuchElementException("Document with id $id not found")
        }
    }

    override suspend fun deleteDocument(document: Document): Result<Unit> {
        return dataSource.deleteDocument(mapper.mapToEntity(document))
    }
}
