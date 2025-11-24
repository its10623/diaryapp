package com.example.diaryapp.di

import com.example.diaryapp.application.validator.DiaryValidateImpl
import com.example.diaryapp.application.validator.DiaryValidator
import com.example.diaryapp.application.validator.FolderValidator
import com.example.diaryapp.application.validator.FolderValidatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ValidatorModule {

    @Provides
    @Singleton
    fun provideDiaryValidator(): DiaryValidator = DiaryValidateImpl()

    @Provides
    @Singleton
    fun provideFolderValidator(): FolderValidator = FolderValidatorImpl()
}