package com.luisfagundes.documents.domain.usecase

import com.luisfagundes.documents.domain.model.Document
import com.luisfagundes.documents.domain.repository.DocumentRepository
import javax.inject.Inject

internal class SaveDocumentUseCase @Inject constructor(
    private val repository: DocumentRepository
) {
    suspend operator fun invoke(document: Document): Result<Unit> {
        return repository.saveDocument(document)
    }
}
