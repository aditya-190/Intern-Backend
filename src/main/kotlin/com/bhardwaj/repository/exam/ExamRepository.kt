package com.bhardwaj.repository.exam

import com.bhardwaj.models.Exam

interface ExamRepository {
    // For Admin
    suspend fun getExamById(examId: String): Exam?
    suspend fun insertExam(exam: Exam): Boolean
    suspend fun updateExam(exam: Exam): Exam?
    suspend fun deleteExam(examId: String): Boolean

    // For Client
    suspend fun getNewExams(): List<Exam>
}