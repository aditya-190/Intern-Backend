package com.bhardwaj.repository.job

import com.bhardwaj.models.job.Job
import com.bhardwaj.models.job.JobResponse

interface JobRepository {
    // For Admin
    suspend fun getJobById(jobId: String): Job
    suspend fun insertJob(job: Job)
    suspend fun insertMultipleJobs(jobs: List<Job>)
    suspend fun updateJob(job: Job): Job
    suspend fun deleteJob(jobId: String)
    suspend fun deleteMultipleJobs(jobsIds: List<String>)
    suspend fun deleteAllJobs()

    // For Client
    suspend fun getNewJobs(page: Int, limit: Int): JobResponse
}