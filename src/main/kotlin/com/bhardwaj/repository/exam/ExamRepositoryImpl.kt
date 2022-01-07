package com.bhardwaj.repository.exam

import com.bhardwaj.models.exam.Exam
import com.bhardwaj.models.exam.ExamResponse
import org.litote.kmongo.coroutine.CoroutineDatabase

class ExamRepositoryImpl(
    private val database: CoroutineDatabase,
) : ExamRepository {
    override suspend fun getExamById(examId: String): Exam {
        TODO("Not yet implemented")
    }

    override suspend fun insertExam(exam: Exam) {
        TODO("Not yet implemented")
    }

    override suspend fun insertMultipleExams(exams: List<Exam>) {
        TODO("Not yet implemented")
    }

    override suspend fun updateExam(exam: Exam): Exam {
        TODO("Not yet implemented")
    }

    override suspend fun deleteExam(examId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMultipleExams(examIds: List<String>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllExams() {
        TODO("Not yet implemented")
    }

    override suspend fun getNewExams(page: Int, limit: Int): ExamResponse {
        TODO("Not yet implemented")
    }
}