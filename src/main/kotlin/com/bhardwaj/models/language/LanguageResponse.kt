package com.bhardwaj.models.language

import kotlinx.serialization.Serializable

@Serializable
data class LanguageResponse(
    val totalLanguages: Int? = null,
    val lastUpdated: Long? = null,
    val languages: List<Language> = emptyList()
)
