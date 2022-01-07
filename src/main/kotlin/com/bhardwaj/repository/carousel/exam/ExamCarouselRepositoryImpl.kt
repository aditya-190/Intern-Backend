package com.bhardwaj.repository.carousel.exam

import com.bhardwaj.models.ExamCarousel
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class ExamCarouselRepositoryImpl(
    database: CoroutineDatabase,
) : ExamCarouselRepository {

    private val examCarouselTable = database.getCollection<ExamCarousel>()

    override suspend fun getExamCarouselById(examCarouselId: String): ExamCarousel? {
        return examCarouselTable.findOne(filter = ExamCarousel::examCarouselId eq examCarouselId)
    }

    override suspend fun insertExamCarousel(examCarousel: ExamCarousel): Boolean {
        return examCarouselTable.insertOne(document = examCarousel).wasAcknowledged()
    }

    override suspend fun deleteExamCarousel(examCarouselId: String): Boolean {
        return examCarouselTable.deleteOne(filter = ExamCarousel::examCarouselId eq examCarouselId).wasAcknowledged()
    }

    override suspend fun updateExamCarousel(examCarousel: ExamCarousel): ExamCarousel? {
        examCarouselTable.updateOne(
            filter = ExamCarousel::examCarouselId eq examCarousel.examCarouselId,
            target = examCarousel
        )
        return examCarouselTable.findOne(filter = ExamCarousel::examCarouselId eq examCarousel.examCarouselId)
    }

    override suspend fun getAllExamCarousel(): List<ExamCarousel> {
        return examCarouselTable.find().ascendingSort(ExamCarousel::placeInCarousel).toList()
    }
}