package com.bhardwaj.repository.language

import com.bhardwaj.models.Language
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class LanguageRepositoryImpl(
    database: CoroutineDatabase,
) : LanguageRepository {

    private val languageTable = database.getCollection<Language>()

    override suspend fun getLanguageById(languageId: String): Language? {
        return languageTable.findOne(filter = Language::languageId eq languageId)
    }

    override suspend fun insertLanguage(language: Language): Boolean {
        if (languageTable.countDocuments(filter = Language::languageName eq language.languageName) > 0) return false
        return languageTable.insertOne(document = language).wasAcknowledged()
    }

    override suspend fun deleteLanguage(languageId: String): Boolean {
        return languageTable.deleteOne(filter = Language::languageId eq languageId).wasAcknowledged()
    }

    override suspend fun updateLanguage(language: Language): Language? {
        languageTable.updateOne(filter = Language::languageId eq language.languageId, target = language)
        return languageTable.findOne(filter = Language::languageId eq language.languageId)
    }

    override suspend fun getAllLanguage(): List<Language> {
        return languageTable.find().ascendingSort(Language::languageNameInEnglish).toList()
    }
}