package com.bhardwaj.repository.language

import com.bhardwaj.models.language.Language
import com.bhardwaj.models.language.LanguageResponse

interface LanguageRepository {
    // For Admin
    suspend fun getLanguageById(languageId: String): Language
    suspend fun insertLanguage(language: Language)
    suspend fun deleteLanguage(languageId: String)
    suspend fun updateLanguage(language: Language): Language

    // For Client
    suspend fun getAllLanguage(): LanguageResponse
}