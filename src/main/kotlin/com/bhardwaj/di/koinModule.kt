package com.bhardwaj.di

import com.bhardwaj.repository.carousel.exam.ExamCarouselRepository
import com.bhardwaj.repository.carousel.exam.ExamCarouselRepositoryImpl
import com.bhardwaj.repository.carousel.job.JobCarouselRepository
import com.bhardwaj.repository.carousel.job.JobCarouselRepositoryImpl
import com.bhardwaj.repository.category.CategoryRepository
import com.bhardwaj.repository.category.CategoryRepositoryImpl
import com.bhardwaj.repository.exam.ExamRepository
import com.bhardwaj.repository.exam.ExamRepositoryImpl
import com.bhardwaj.repository.filter.FilterRepository
import com.bhardwaj.repository.filter.FilterRepositoryImpl
import com.bhardwaj.repository.job.JobRepository
import com.bhardwaj.repository.job.JobRepositoryImpl
import com.bhardwaj.repository.language.LanguageRepository
import com.bhardwaj.repository.language.LanguageRepositoryImpl
import com.bhardwaj.repository.user.UserRepository
import com.bhardwaj.repository.user.UserRepositoryImpl
import com.bhardwaj.utils.ConnectionString.connString
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val koinModule = module {
    single {
        KMongo.createClient(connectionString = connString)
            .coroutine
            .getDatabase("intern_db")
    }

    single<CategoryRepository> {
        CategoryRepositoryImpl(get())
    }

    single<ExamRepository> {
        ExamRepositoryImpl(get())
    }

    single<FilterRepository> {
        FilterRepositoryImpl(get())
    }

    single<JobRepository> {
        JobRepositoryImpl(get())
    }

    single<LanguageRepository> {
        LanguageRepositoryImpl(get())
    }

    single<UserRepository> {
        UserRepositoryImpl(get())
    }

    single<ExamCarouselRepository> {
        ExamCarouselRepositoryImpl(get())
    }

    single<JobCarouselRepository> {
        JobCarouselRepositoryImpl(get())
    }
}