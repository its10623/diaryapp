package com.example.diaryapp.di

import com.example.diaryapp.domain.usecase.diary.DiaryUseCase
import com.example.diaryapp.domain.usecase.diary.DiaryUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindDiaryUseCase(
        impl: DiaryUseCaseImpl
    ): DiaryUseCase
}