package com.bhardwaj.repository.language

import com.bhardwaj.models.Language

interface LanguageRepository {
    // For Admin
    suspend fun getLanguageById(languageId: String): Language?
    suspend fun insertLanguage(language: Language): Boolean
    suspend fun deleteLanguage(languageId: String): Boolean
    suspend fun updateLanguage(language: Language): Language?

    // For Client
    suspend fun getAllLanguage(): List<Language>
}