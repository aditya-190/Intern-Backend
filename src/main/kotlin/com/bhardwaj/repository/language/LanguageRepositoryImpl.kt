package com.bhardwaj.repository.language

import com.bhardwaj.models.language.Language
import com.bhardwaj.models.language.LanguageResponse
import org.litote.kmongo.coroutine.CoroutineDatabase

class LanguageRepositoryImpl(
    private val database: CoroutineDatabase,
) : LanguageRepository {
    override suspend fun getLanguageById(languageId: String): Language {
        TODO("Not yet implemented")
    }

    override suspend fun insertLanguage(language: Language) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteLanguage(languageId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateLanguage(language: Language): Language {
        TODO("Not yet implemented")
    }

    override suspend fun getAllLanguage(): LanguageResponse {
        TODO("Not yet implemented")
    }
}