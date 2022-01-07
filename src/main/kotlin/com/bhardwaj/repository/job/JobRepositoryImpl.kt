package com.bhardwaj.repository.job

import com.bhardwaj.models.job.Job
import com.bhardwaj.models.job.JobResponse
import org.litote.kmongo.coroutine.CoroutineDatabase

class JobRepositoryImpl(
    private val database: CoroutineDatabase,
) : JobRepository {
    override suspend fun getJobById(jobId: String): Job {
        TODO("Not yet implemented")
    }

    override suspend fun insertJob(job: Job) {
        TODO("Not yet implemented")
    }

    override suspend fun insertMultipleJobs(jobs: List<Job>) {
        TODO("Not yet implemented")
    }

    override suspend fun updateJob(job: Job): Job {
        TODO("Not yet implemented")
    }

    override suspend fun deleteJob(jobId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMultipleJobs(jobsIds: List<String>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllJobs() {
        TODO("Not yet implemented")
    }

    override suspend fun getNewJobs(page: Int, limit: Int): JobResponse {
        TODO("Not yet implemented")
    }
}