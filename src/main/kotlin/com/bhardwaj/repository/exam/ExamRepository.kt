package com.bhardwaj.repository.exam

import com.bhardwaj.models.exam.Exam
import com.bhardwaj.models.exam.ExamResponse

interface ExamRepository {
    // For Admin
    suspend fun getExamById(examId: String): Exam
    suspend fun insertExam(exam: Exam)
    suspend fun insertMultipleExams(exams: List<Exam>)
    suspend fun updateExam(exam: Exam): Exam
    suspend fun deleteExam(examId: String)
    suspend fun deleteMultipleExams(examIds: List<String>)
    suspend fun deleteAllExams()

    // For Client
    suspend fun getNewExams(page: Int, limit: Int): ExamResponse
}