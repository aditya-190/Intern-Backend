package com.bhardwaj.di

import com.bhardwaj.repository.*
import com.bhardwaj.utils.Constants.DATABASE_NAME
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val koinModule = module {
    single {
        KMongo.createClient()
            .coroutine
            .getDatabase(DATABASE_NAME)
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
}