package com.bhardwaj.repository.carousel.job

import com.bhardwaj.models.JobCarousel
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class JobCarouselRepositoryImpl(
    database: CoroutineDatabase,
) : JobCarouselRepository {

    private val jobCarouselTable = database.getCollection<JobCarousel>()

    override suspend fun getJobCarouselById(jobCarouselId: String): JobCarousel? {
        return jobCarouselTable.findOne(filter = JobCarousel::jobCarouselId eq jobCarouselId)
    }

    override suspend fun insertJobCarousel(jobCarousel: JobCarousel): Boolean {
        if (jobCarouselTable.countDocuments(filter = JobCarousel::jobCarouselImage eq jobCarousel.jobCarouselImage) > 0) return false
        return jobCarouselTable.insertOne(document = jobCarousel).wasAcknowledged()
    }

    override suspend fun deleteJobCarousel(jobCarouselId: String): Boolean {
        return jobCarouselTable.deleteOne(filter = JobCarousel::jobCarouselId eq jobCarouselId).wasAcknowledged()
    }

    override suspend fun updateJobCarousel(jobCarousel: JobCarousel): JobCarousel? {
        jobCarouselTable.updateOne(
            filter = JobCarousel::jobCarouselId eq jobCarousel.jobCarouselId,
            target = jobCarousel
        )
        return jobCarouselTable.findOne(filter = JobCarousel::jobCarouselId eq jobCarousel.jobCarouselId)
    }

    override suspend fun getAllJobCarousel(): List<JobCarousel> {
        return jobCarouselTable.find().ascendingSort(JobCarousel::placeInCarousel).toList()
    }
}