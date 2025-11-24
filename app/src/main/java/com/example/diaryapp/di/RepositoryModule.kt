package com.example.diaryapp.di

import com.example.diaryapp.data.repository.DiaryRepositoryImpl
import com.example.diaryapp.data.repository.UserRepositoryImpl
import com.example.diaryapp.domain.repository.DiaryRepository
import com.example.diaryapp.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        impl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindDiaryRepository(
        impl: DiaryRepositoryImpl
    ): DiaryRepository
}