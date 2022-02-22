package com.bhardwaj.repository.job

import com.bhardwaj.models.Job
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class JobRepositoryImpl(
    database: CoroutineDatabase,
) : JobRepository {

    private val jobTable = database.getCollection<Job>()

    override suspend fun getJobById(jobId: String): Job? {
        return jobTable.findOne(filter = Job::postId eq jobId)
    }

    override suspend fun insertJob(job: Job): Boolean {
        if (jobTable.find(
                filters = arrayOf(
                    Job::companyName eq job.companyName,
                    Job::postTitle eq job.postTitle,
                    Job::applyNowPage eq job.applyNowPage
                )
            ).toList().isNotEmpty()
        ) return false
        return jobTable.insertOne(document = job).wasAcknowledged()
    }

    override suspend fun insertMultipleJob(jobs: List<Job>): Boolean {
        return jobTable.insertMany(jobs).wasAcknowledged()
    }

    override suspend fun updateJob(job: Job): Boolean {
        if (jobTable.find(
                filters = arrayOf(
                    Job::companyName eq job.companyName,
                    Job::postTitle eq job.postTitle,
                    Job::applyNowPage eq job.applyNowPage
                )
            ).toList().isNotEmpty()
        ) return false

        return jobTable.updateOne(filter = Job::postId eq job.postId, target = job).wasAcknowledged()
    }

    override suspend fun deleteJob(jobId: String): Boolean {
        return jobTable.deleteOne(filter = Job::postId eq jobId).wasAcknowledged()
    }

    override suspend fun getNewJobs(page: Int, limit: Int): List<Job> {
        return jobTable.find().skip(skip = (page - 1) * limit).limit(limit = limit)
            .partial(true).descendingSort(Job::lastUpdated).toList()
    }
}