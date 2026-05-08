package com.luisfagundes.documents.di

import com.luisfagundes.common.data.database.TripDatabase
import com.luisfagundes.documents.data.dao.DocumentDao
import com.luisfagundes.documents.data.datasource.DocumentLocalDataSource
import com.luisfagundes.documents.data.datasource.DocumentLocalDataSourceImpl
import com.luisfagundes.documents.data.mapper.DocumentMapper
import com.luisfagundes.documents.data.repository.DocumentRepositoryImpl
import com.luisfagundes.documents.domain.repository.DocumentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DocumentDataModule {

    @Provides
    @Singleton
    fun provideDocumentDao(database: TripDatabase): DocumentDao {
        return database.documentDao()
    }

    @Provides
    @Singleton
    fun provideDocumentLocalDataSource(dao: DocumentDao): DocumentLocalDataSource {
        return DocumentLocalDataSourceImpl(dao)
    }

    @Provides
    @Singleton
    fun provideDocumentMapper(): DocumentMapper {
        return DocumentMapper()
    }

    @Provides
    @Singleton
    fun provideDocumentRepository(
        dataSource: DocumentLocalDataSource,
        mapper: DocumentMapper
    ): DocumentRepository {
        return DocumentRepositoryImpl(dataSource, mapper)
    }
}
