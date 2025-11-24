package com.example.diaryapp.di

import com.example.diaryapp.application.usecase.DiaryUseCase
import com.example.diaryapp.application.usecase.DiaryUseCaseImpl
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