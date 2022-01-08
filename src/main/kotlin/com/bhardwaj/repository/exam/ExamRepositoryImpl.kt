package com.bhardwaj.repository.exam

import com.bhardwaj.models.Exam
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class ExamRepositoryImpl(
    database: CoroutineDatabase,
) : ExamRepository {

    private val examTable = database.getCollection<Exam>()

    override suspend fun getExamById(examId: String): Exam? {
        return examTable.findOne(filter = Exam::postId eq examId)
    }

    override suspend fun insertExam(exam: Exam): Boolean {
        return examTable.insertOne(document = exam).wasAcknowledged()
    }

    override suspend fun updateExam(exam: Exam): Exam? {
        examTable.updateOne(filter = Exam::postId eq exam.postId, target = exam)
        return examTable.findOne(filter = Exam::postId eq exam.postId)
    }

    override suspend fun deleteExam(examId: String): Boolean {
        return examTable.deleteOne(filter = Exam::postId eq examId).wasAcknowledged()
    }

    override suspend fun getNewExams(): List<Exam> {
        return examTable.find().descendingSort(Exam::lastUpdated).toList()
    }
}