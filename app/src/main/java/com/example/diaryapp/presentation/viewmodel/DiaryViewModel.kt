package com.example.diaryapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diaryapp.domain.usecase.diary.DiaryUseCase
import com.example.diaryapp.data.dto.DiaryDto
import com.example.diaryapp.presentation.ui.component.timeline.SortType
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

    // 폴더 리스트 상태
    private val _folderDiaryList = MutableStateFlow<List<DiaryDto>>(emptyList())
    val folderDiaryList: StateFlow<List<DiaryDto>> = _folderDiaryList.asStateFlow()

    private val _timelineSort = MutableStateFlow(SortType.RECENT)
    val timelineSortType = _timelineSort.asStateFlow()

    private val _timelineDates = MutableStateFlow<Set<LocalDate>>(emptySet())
    val timelineDates = _timelineDates.asStateFlow()

    private val _folderSort = MutableStateFlow(SortType.RECENT)
    val folderSortType = _folderSort.asStateFlow()

    private val _folderDates = MutableStateFlow<Set<LocalDate>>(emptySet())
    val folderDates = _folderDates.asStateFlow()

    private val _selectedDiary = MutableStateFlow<DiaryDto?>(null)
    val selectedDiary = _selectedDiary.asStateFlow()

    private val _activeFilter = MutableStateFlow(false)
    val activeFilter = _activeFilter.asStateFlow()

    private val _pendingDeleteId = MutableStateFlow<Int?>(null)
    val pendingDeleteId: StateFlow<Int?> = _pendingDeleteId

    fun prepareDelete(id: Int) {
        _pendingDeleteId.value = id
    }

    fun clearPendingDelete() {
        _pendingDeleteId.value = null
    }

    // 어떤 화면에서 필터를 사용하는지 저장
    private val _currentFilterScope = MutableStateFlow(FilterScope.TIMELINE)
    val currentFilterScope = _currentFilterScope.asStateFlow()

    // 타임라인 스크린 적용 필터
    val filteredDiaryList: StateFlow<List<DiaryDto>> =
        combine(
            _diaryList,
            _timelineSort,
            _timelineDates
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
                            it.createDate.time
                        }

                        SortType.OLD -> list.sortedBy {
                            it.createDate.time
                        }
                    }
                }
        }.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            emptyList()
        )

    // 폴더 스크린 적용 필터
    val filteredFolderList: StateFlow<List<DiaryDto>> =
        combine(
            _folderDiaryList,
            _folderSort,
            _folderDates
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
                        SortType.RECENT -> list.sortedByDescending { it.createDate.time }
                        SortType.OLD -> list.sortedBy { it.createDate.time }
                    }
                }
        }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun loadDiary(id: Int) {
        viewModelScope.launch {
            diaryUseCase.getDiaryById(id)
                .catch { e -> _errorMessage.value = e.message }
                .collect { diary -> _selectedDiary.value = diary }
        }
    }

    private val _folders = MutableStateFlow(emptyList<String>())
    val folders: StateFlow<List<String>> = _folders.asStateFlow()

    fun loadFolders(userName: String) {
        viewModelScope.launch {
            diaryUseCase.getFolders(userName)
                .catch { e -> _errorMessage.value = e.message }
                .collect { list -> _folders.value = list.toList() }
        }
    }

    fun loadUserDiaries(userName: String) {
        viewModelScope.launch {
            diaryUseCase.getDiariesByUser(userName)
                .catch { e -> _errorMessage.value = e.message }
                .collect { list -> _diaryList.value = list.toList() }
        }
    }

    fun loadFolderDiaries(userName: String, folder: String) {
        viewModelScope.launch {
            diaryUseCase.getDiariesByFolder(userName, folder)
                .catch { e -> _errorMessage.value = e.message }
                .collect { list -> _folderDiaryList.value = list.toList() }
        }
    }

    fun loadFavoriteDiaries(userName: String) {
        viewModelScope.launch {
            diaryUseCase.getFavoriteDiaries(userName)
                .catch { e -> _errorMessage.value = e.message }
                .collect { list -> _diaryList.value = list.toList() }
        }
    }

    fun toggleFavoriteStatus(id: Int, isFavorite: Boolean, userName: String) {
        viewModelScope.launch {
            try {
                diaryUseCase.toggleFavoriteStatus(id, isFavorite)
                loadUserDiaries(userName)
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun searchInFolder(user: String, folder: String, keyword: String) {
        viewModelScope.launch {
            diaryUseCase.searchInFolder(user, folder, keyword)
                .catch { e -> _errorMessage.value = e.message }
                .collect { _folderDiaryList.value = it.toList() }
        }
    }

    fun searchTimeline(userName: String, keyword: String) {
        viewModelScope.launch {
            diaryUseCase.searchTimeline(userName, keyword)
                .catch { _errorMessage.value = it.message }
                .collect { _diaryList.value = it.toList() }
        }
    }

    fun filterDate(userName: String, start: Long, end: Long) {
        viewModelScope.launch {
            diaryUseCase.filterByDate(userName, start, end)
                .catch { e -> _errorMessage.value = e.message }
                .collect { list -> _diaryList.value = list.toList() }
        }
    }

    fun writeDiary(diary: DiaryDto) {
        viewModelScope.launch {
            try {
                diaryUseCase.writeDiary(diary)
                _writeSuccess.emit(true)
                loadUserDiaries(diary.userName)
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun writeDiary(
        userName: String,
        folder: String?,
        title: String,
        content: String
    ) {
        viewModelScope.launch {
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
    }

    fun updateDiary(diary: DiaryDto) {
        viewModelScope.launch {
            try {
                diaryUseCase.updateDiary(diary)
                _updateSuccess.emit(true)
                loadUserDiaries(diary.userName)
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun deleteDiary(id: Int, userName: String) {
        viewModelScope.launch {
            try {
                diaryUseCase.deleteDiary(id)
                _selectedDiary.value = null
                _deleteSuccess.emit(true)
                loadUserDiaries(userName)
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun addFolder(userName: String, folderName: String) {
        viewModelScope.launch {
            try {
                diaryUseCase.addFolder(userName, folderName)
                loadFolders(userName)
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun setFilterScope(scope: FilterScope) {
        _currentFilterScope.value = scope
    }

    //필터 적용
    fun applyFilter(sort: SortType, dates: Set<LocalDate>) {
        when (_currentFilterScope.value) {
            FilterScope.TIMELINE -> {
                _timelineSort.value = sort
                _timelineDates.value = dates
            }

            FilterScope.FOLDER -> {
                _folderSort.value = sort
                _folderDates.value = dates
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }

    fun clearSelected() {
        _selectedDiary.value = null
    }
}

enum class FilterScope { TIMELINE, FOLDER }