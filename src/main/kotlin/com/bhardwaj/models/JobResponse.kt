package com.bhardwaj.models

import kotlinx.serialization.Serializable

@Serializable
data class JobResponse(
    val totalNewJobs: Int? = null,
    val jobsPerResponse: Int? = null,
    val previousPage: Int? = null,
    val nextPage: Int? = null,
    val lastUpdated: Long? = null,
    val jobs: List<Job> = emptyList()
)
