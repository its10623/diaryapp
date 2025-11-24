# 🗒️ 모노 다이어리 ( Android / Jetpack Compose )

> 모노톤 감성의 개인 다이어리 Android Application 입니다.
> 폴더 분류 / 타임라인 / 일기 작성 기능을 제공하며,
> Jetpack Compose / Clean Architecture / MVVM 기반으로 구현 되었습니다.

---

## 🧩 기술 스택

### 🛠️ 개발 언어 & 코어
- **Kotlin 1.9.x**
- **Gradle KTS** 기반 빌드 구성

### 🎨 UI & UX
- **Jetpack Compose**
- Material3 + Custom Theme
- Drawer / Bottom Navigation
- Modal BottomSheet, LazyColumn

### 🧱 아키텍처
- **Clean Architecture**
- **MVVM**
- 단방향 Data Flow(StateFlow 기반)

### 💾 데이터 & 저장소
- **Room** (User / Diary / Folder Entity)
- **DataStore Preferences** (자동 로그인)

### 🔌 의존성 주입(DI)
- **Hilt** + Hilt Navigation Compose

### 🔐 인증 & 보안
- **BCrypt** 기반 비밀번호 해시 처리

---

## 🧪 실행 방법

### 1. Android Studio 설치
최신 Android Studio(Flamingo 이상) 설치

### 2. 빌드
Terminal 또는 IDE에서:
./gradlew assembleDebug

### 3. 실행
에뮬레이터 또는 실제 기기에서 실행

### 4. 첫 실행 후
- 회원가입 → 로그인  
- 자동 로그인 사용 시 DataStore에 저장됨  

---

## 🔄 다이어리 기능 흐름도

### 🔐 로그인 흐름
UI(LoginScreen)  
→ ViewModel(LoginViewModel)  
→ LoginUseCase  
→ UserRepository  
→ Room(UserEntity)

자동 로그인:  
UI → DataStore → tryAutoLogin()

---

### 📄 다이어리 생성 흐름
EditorScreen  
→ DiaryViewModel  
→ DiaryUseCase  
→ DiaryRepository  
→ Room(DiaryEntity)

---

### 🔎 필터 흐름
FilterBottomSheet  
→ DiaryViewModel.applyFilter()  
→ StateFlow 업데이트  
→ Timeline/Folder 화면 자동 반영

- 타임라인 필터는 타임라인만 적용
- 폴더 필터는 폴더만 적용 (FilterScope 기반)

---

## 🚀 주요 기능

### 👤 사용자 기능
- 회원가입 / 로그인
- 자동 로그인(DataStore 기반)
- 저장된 UserName 데이터 기반으로 비밀번호 변경
- 로그아웃

### 📝 다이어리 기능
- Timline 기반 다이어리 Preview
- 일기 작성 / 수정 / 삭제
- Custom 폴더별 분류
- 즐겨찾기(Favorites)
- 검색 기능 (타임라인/폴더 각각 독립 검색)
- 정렬 (최신순 / 오래된순)
- 날짜 필터 (LocalDate 기반, 화면별 독립 필터)
- 폴더 생성 및 삭제 기능

### 🎨 UI/UX
- Jetpack Compose 기반 UI
- Drawer Navigation
- Bottom Navigation
- Modal BottomSheet 필터 기능
- LazyColumn 기반 다이어리 뷰 화면
- 에디터 화면에서 실시간 상태 관리

---

```
## 📁 프로젝트 구조
com.example.diaryapp
│
├── data                                         # 실제 데이터 접근 (Room, DataStore, RepositoryImpl)
│   ├── local
│   │   ├── datastore                            # DataStore (자동 로그인 등)
│   │   │   ├── UserLocalDataSource.kt
│   │   │   └── UserPreferencesSerializer.kt
│   │   └── room                                 # Room DB (Entity, Dao, Database)
│   │       ├── Converters.kt
│   │       ├── DiaryDao.kt
│   │       ├── DiaryDatabase.kt
│   │       ├── DiaryEntity.kt
│   │       ├── FolderDao.kt
│   │       └── FolderEntity.kt
│   │
│   ├── dto                                      # 계층간 전달 객체 (Entity <-> Domain)
│   │   └── DiaryDto.kt
│   │
│   ├── mapper                                   # Entity <-> Domain 변환
│   │   └── DiaryMapper.kt
│   │
│   └── repository                               # Repository 구현체
│       ├── DiaryRepositoryImpl.kt
│       └── UserRepositoryImpl.kt
│
├── domain                                       # 비즈니스 규칙, 엔티티, 유스케이스
│   ├── model                                    # 순수 도메인 엔티티 (UI/DB와 독립)
│   │   ├── Diary.kt
│   │   └── User.kt
│   │
│   ├── repository                               # Repository 추상 인터페이스
│   │   ├── DiaryRepository.kt
│   │   └── UserRepository.kt
│   │
│   ├── validator                                # Domain 검증 규칙 (title/content,folder)
│   │   ├── DiaryValidator.kt
│   │   ├── DiaryValidatorImpl.kt
│   │   ├── FolderValidator.kt
│   │   └── FolderValidatorImpl.kt
│   │
│   └── usecase                                  # 유스케이스 (앱의 비즈니스 흐름)
│       ├── diary
│       │   ├── DiaryUseCase.kt
│       │   └── DiaryUseCaseImpl.kt
│       └── user
│           ├── LoginUseCase.kt
│           ├── RegisterUseCase.kt
│           ├── AutoLoginUseCase.kt
│           └── FindPasswordUseCase.kt
│
├── presentation                                 # UI / ViewModel / Navigation
│   ├── ui
│   │   ├── component                            # 재사용 컴포넌트 (버튼, 카드 등)
│   │   │   ├── button
│   │   │   │   ├── BackButton.kt
│   │   │   │   ├── Button.kt
│   │   │   │   ├── LoginButton.kt
│   │   │   │   ├── MoreButton.kt
│   │   │   │   ├── TextOnlyButton.kt
│   │   │   │   └── WriteFab.kt
│   │   │   │
│   │   │   ├── card
│   │   │   │   ├── LoginCard.kt
│   │   │   │   └── TimelineCard.kt
│   │   │   │
│   │   │   ├── textfield
│   │   │   │   ├── IdTextField.kt
│   │   │   │   └── PasswordTextField.kt
│   │   │   │
│   │   │   ├── logo
│   │   │   │   └── Logo.kt
│   │   │   │
│   │   │   └── timeline
│   │   │       ├── DateHeader.kt
│   │   │       ├── TimelineEmptyView.kt
│   │   │       ├── TimelineIndicator.kt
│   │   │       ├── filterBottomSheet.kt
│   │   │       ├── DrawerContent.kt
│   │   │       ├── EditorMode.kt
│   │   │       ├── BottomNavBar.kt
│   │   │       └── DiarySearchBar.kt
│   │   │
│   │   ├── navigation                            # AppNavgation / route 정의
│   │   │   └── AppNavigation.kt
│   │   │
│   │   ├── screen
│   │   │   ├── findPassword
│   │   │   │   ├── FindPasswordScreen.kt
│   │   │   │   ├── ResetPasswordScreen.kt
│   │   │   │   └── FindPasswordFlow.kt
│   │   │   │
│   │   │   ├── Screen.kt                          # 실제 화면
│   │   │   ├── LoginScreen.kt
│   │   │   ├── SignUpScreen.kt
│   │   │   ├── SplashScreen.kt
│   │   │   ├── MainScreen.kt
│   │   │   ├── CommonScreen.kt
│   │   │   ├── TimelineScreen.kt
│   │   │   ├── FolderScreen.kt
│   │   │   ├── FavoritesScreen.kt
│   │   │   ├── CalendarScreen.kt
│   │   │   ├── ProfileScreen.kt
│   │   │   ├── DiaryEditorScreen.kt
│   │   │   └── DiaryViewScreen.kt
│   │   │
│   │   ├── event                                  # Ui Event (sealed class)
│   │   │   ├── FindPasswordEvent.kt
│   │   │   ├── ResetPasswordEvent.kt
│   │   │   ├── RegisterEvent.kt
│   │   │   └── LoginEvent.kt
│   │   │
│   │   ├── uiState                                # Ui 상태 모델
│   │   │   ├── FindPasswordUiState.kt
│   │   │   ├── ResetPasswordUiState.kt
│   │   │   ├── RegisterUiState.kt
│   │   │   └── LoginUiState.kt
│   │   │
│   │   ├── theme
│   │   │   ├── Color.kt
│   │   │   ├── Theme.kt
│   │   │   └── Type.kt
│   │   │
│   │   └── dialog                                 # 공용 Dialog Ui
│   │       └── Dialog.kt
│   │
│   └── viewmodel                                  # 화면별 ViewModel (상태 관리 / 유스케이스 호출)
│       ├── DiaryViewModel.kt
│       ├── LoginViewModel.kt
│       ├── RegisterViewModel.kt
│       ├── FindPasswordViewModel.kt
│       └── ResetPasswordViewModel.kt
│
├── di                           # Hilt DI 모듈
│   ├── DataStoreModule.kt
│   ├── RepositoryModule.kt
│   ├── UseCaseModule.kt
│   └── ValidatorModule.kt
│
├── DiaryApp.kt                  # Application 클래스
├── MainActivity.kt              # Single Activity
└── SplashActivity.kt            # 초기 진입 Activity
```

