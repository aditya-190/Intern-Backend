package com.bhardwaj.repository

import com.bhardwaj.models.Language

interface LanguageRepository {
    val languages: List<Language>
}