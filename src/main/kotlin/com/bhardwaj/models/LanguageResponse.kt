package com.bhardwaj.models

import kotlinx.serialization.Serializable

@Serializable
data class LanguageResponse(
    val totalLanguages: Int? = null,
    val languagesPerResponse: Int? = null,
    val previousPage: Int? = null,
    val nextPage: Int? = null,
    val lastUpdated: Long? = null,
    val languages: List<Language> = emptyList()
)
