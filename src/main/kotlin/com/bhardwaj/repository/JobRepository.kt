package com.bhardwaj.repository

import com.bhardwaj.models.Job

interface JobRepository {
    val jobs: List<Job>
}