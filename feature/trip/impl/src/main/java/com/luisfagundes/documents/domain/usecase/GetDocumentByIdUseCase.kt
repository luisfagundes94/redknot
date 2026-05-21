package com.luisfagundes.documents.domain.usecase

import com.luisfagundes.documents.domain.model.Document
import com.luisfagundes.documents.domain.repository.DocumentRepository
import javax.inject.Inject

internal class GetDocumentByIdUseCase @Inject constructor(
    private val repository: DocumentRepository
) {
    suspend operator fun invoke(id: Int): Result<Document> {
        return repository.getDocumentById(id)
    }
}
