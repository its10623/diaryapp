package com.example.diaryapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diaryapp.application.usecase.DiaryUseCase
import com.example.diaryapp.dto.DiaryDto
import com.example.diaryapp.presentation.ui.component.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class DiaryViewModel @Inject constructor(
    private val diaryUseCase: DiaryUseCase
) : ViewModel() {

    // UI 상태
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    private val _writeSuccess = MutableSharedFlow<Boolean>()
    val writeSuccess = _writeSuccess.asSharedFlow()

    private val _updateSuccess = MutableSharedFlow<Boolean>()
    val updateSuccess = _updateSuccess.asSharedFlow()

    private val _deleteSuccess = MutableSharedFlow<Boolean>()
    val deleteSuccess = _deleteSuccess.asSharedFlow()

    // 다이어리 리스트 상태
    private val _diaryList = MutableStateFlow(emptyList<DiaryDto>())
    val diaryList: StateFlow<List<DiaryDto>> = _diaryList.asStateFlow()


    private val _sortType = MutableStateFlow(SortType.RECENT)
    val sortType = _sortType.asStateFlow()

    private val _selectedDates = MutableStateFlow<Set<LocalDate>>(emptySet())
    val selectedDates = _selectedDates.asStateFlow()

    private val _selectedDiary = MutableStateFlow<DiaryDto?>(null)
    val selectedDiary = _selectedDiary.asStateFlow()

    private val _activeFilter = MutableStateFlow(false)
    val activeFilter = _activeFilter.asStateFlow()

    val filteredDiaryList: StateFlow<List<DiaryDto>> =
        combine(
            _diaryList,
            _sortType,
            _selectedDates
        ) { diaries, sort, dates ->

            diaries
                .filter { diary ->
                    if (dates.isEmpty()) true
                    else {
                        val diaryDate = Instant.ofEpochMilli(diary.createDate.time)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()

                        diaryDate in dates
                    }
                }
                .let { list ->
                    when (sort) {
                        SortType.NONE -> list
                        SortType.RECENT -> list.sortedByDescending {
                            Instant.ofEpochMilli(it.createDate.time)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                        }
                        SortType.OLD -> list.sortedBy {
                            Instant.ofEpochMilli(it.createDate.time)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                        }
                    }
                }
        }.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            emptyList()
        )

    fun loadDiary(id: Int) {
        viewModelScope.launch {
            diaryUseCase.getDiaryById(id)
                .catch { e -> _errorMessage.value = e.message }
                .collect { diary -> _selectedDiary.value = diary }
        }
    }

    // Folders
    val folders: StateFlow<List<String>> =
        MutableStateFlow(emptyList<String>())
    private val _folders = MutableStateFlow(emptyList<String>())

    fun loadFolders(userName: String) {
        viewModelScope.launch {
            diaryUseCase.getFolders(userName)
                .catch { e -> _errorMessage.value = e.message }
                .collect { list -> _folders.value = list }
        }
    }

    fun loadUserDiaries(userName: String) {
        viewModelScope.launch {
            diaryUseCase.getDiariesByUser(userName)
                .catch { e -> _errorMessage.value = e.message }
                .collect { list -> _diaryList.value = list }
        }
    }

    fun loadFolderDiaries(userName: String, folder: String) {
        viewModelScope.launch {
            diaryUseCase.getDiariesByFolder(userName, folder)
                .catch { e -> _errorMessage.value = e.message }
                .collect { list -> _diaryList.value = list }
        }
    }

    fun searchInFolder(user: String, folder: String, keyword: String) {
        viewModelScope.launch {
            diaryUseCase.searchInFolder(user, folder, keyword)
                .catch { e -> _errorMessage.value = e.message }
                .collect { _diaryList.value = it }
        }
    }

    fun searchTimeline(userName: String, keyword: String) {
        viewModelScope.launch {
            diaryUseCase.searchTimeline(userName, keyword)
                .catch { _errorMessage.value = it.message }
                .collect { _diaryList.value = it }
        }
    }

    fun filterDate(userName: String, start: Long, end: Long) {
        viewModelScope.launch {
            diaryUseCase.filterByDate(userName, start, end)
                .catch { e -> _errorMessage.value = e.message }
                .collect { list -> _diaryList.value = list }
        }
    }

    fun writeDiary(diary: DiaryDto) {
        viewModelScope.launch {
            try {
                diaryUseCase.writeDiary(diary)
                _writeSuccess.emit(true)
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    // Ui에서 쓰기 편하게 오버로드
    fun writeDiary(
        userName: String,
        folder: String?,
        title: String,
        content: String
    ) {
        val now = java.util.Date()
        val diary = DiaryDto(
            userName = userName,
            folder = folder,
            title = title,
            content = content,
            createDate = now,
            updateDate = now
        )
        writeDiary(diary)
    }

    fun updateDiary(diary: DiaryDto) {
        viewModelScope.launch {
            try {
                diaryUseCase.updateDiary(diary)
                _updateSuccess.emit(true)
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun deleteDiary(id: Int) {
        viewModelScope.launch {
            try {
                diaryUseCase.deleteDiary(id)
                _deleteSuccess.emit(true)
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    //필터 적용
    fun applyFilter(
        sort: SortType,
        dates: Set<LocalDate>
    ) {
        _sortType.value = sort
        _selectedDates.value = dates
        _activeFilter.value = sort != SortType.NONE || dates.isNotEmpty()
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}