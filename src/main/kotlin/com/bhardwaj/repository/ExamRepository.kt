package com.bhardwaj.repository

import com.bhardwaj.models.Exam

interface ExamRepository {
    val exams: List<Exam>
}