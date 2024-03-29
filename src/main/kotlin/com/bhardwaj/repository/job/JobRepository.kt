package com.bhardwaj.repository.job

import com.bhardwaj.models.Job

interface JobRepository {
    // For Admin
    suspend fun getJobById(jobId: String): Job?
    suspend fun insertJob(job: Job): Boolean
    suspend fun insertMultipleJob(jobs: List<Job>): Boolean
    suspend fun updateJob(job: Job): Boolean
    suspend fun deleteJob(jobId: String): Boolean

    // For Client
    suspend fun getNewJobs(page: Int, limit: Int): List<Job>
}