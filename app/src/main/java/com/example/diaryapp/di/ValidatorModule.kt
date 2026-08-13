package com.example.diaryapp.di

import com.example.diaryapp.domain.validator.DiaryValidateImpl
import com.example.diaryapp.domain.validator.DiaryValidator
import com.example.diaryapp.domain.validator.FolderValidator
import com.example.diaryapp.domain.validator.FolderValidatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ValidatorModule {

    @Binds
    @Singleton
    abstract fun bindDiaryValidator(impl: DiaryValidateImpl): DiaryValidator

    @Binds
    @Singleton
    abstract fun bindFolderValidator(impl: FolderValidatorImpl): FolderValidator
}
