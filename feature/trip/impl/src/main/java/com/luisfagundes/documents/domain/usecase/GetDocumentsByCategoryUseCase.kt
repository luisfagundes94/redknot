package com.luisfagundes.documents.domain.usecase

import com.luisfagundes.documents.domain.model.Document
import com.luisfagundes.documents.domain.model.DocumentCategory
import com.luisfagundes.documents.domain.repository.DocumentRepository
import javax.inject.Inject

internal class GetDocumentsByCategoryUseCase @Inject constructor(
    private val repository: DocumentRepository
) {
    suspend operator fun invoke(tripId: Int): Result<Map<DocumentCategory, List<Document>>> {
        return repository.getDocumentsByTripId(tripId).map { items ->
            items.groupBy { it.category }
        }
    }
}