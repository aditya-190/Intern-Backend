package com.bhardwaj.models

import kotlinx.serialization.Serializable

@Serializable
data class ExamResponse(
    val totalNewExam: Int? = null,
    val examsPerResponse: Int? = null,
    val previousPage: Int? = null,
    val nextPage: Int? = null,
    val lastUpdated: Long? = null,
    val exams: List<Exam> = emptyList()
)
