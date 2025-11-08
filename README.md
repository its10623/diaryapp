# 🗒️ Diary App

> Kotlin 기반 콘솔 다이어리 애플리케이션
> 회원가입, 로그인, 일기 작성/조회/삭제 기능을 지원하며  
> 이후 Android UI로 확장될 예정입니다.

---

## 📌 프로젝트 개요

|     항목     |                  내용                |
|-------------|-------------------------------------|
| **프로젝트명** |      Kotlin Diary Console App       |
| **개발 언어** |              Kotlin (JVM)           |
| **실행 환경** |                                     |
|   **목표**   | 클린 아키텍처, MVVM 및 DDD 다이어리 앱 구현 |

---

## 🧩 기술 스택

|      구분     |               사용 기술              |
|--------------|------------------------------------|
|   Language   |            Kotlin 1.9.x            |
|  Build Tool  |            Gradle (KTS)            |
| Architecture |    Clean Architecture, MVVM, DDD   |
|  Encryption  |  BCrypt (`org.mindrot:jbcrypt:0.4`)|
|     Test     |         JUnit5, AssertJ            | 활용 예정

---

## 🧪 실행 방법
추가 예정

---

## 🚀 주요 기능

- ✅ **회원가입 / 로그인**
  - BCrypt로 비밀번호 암호화 저장
  - 중복 사용자 검증 및 예외 처리

- 📝 **일기 CRUD**
  - 일기 작성 / 조회 / 수정 / 삭제
  - 제목 중복 시 (1), (2) 자동 생성
  - 파일(.txt) 기반 저장 및 자동 날짜 기록

- ⚙️ **입력 검증**
  - 제목, 내용, 비밀번호 등 유효성 검사
  - 잘못된 입력 시 예외 메시지 출력

- 🔒 **아키텍처**
  - Clean Architecture + MVVM 적용
  - domain, data, application, presentation, viewmodel 계층 분리

---

- 🧠 향후 계획
  -	File → SQLite(Room) 전환
  -	Android UI(ViewModel + Compose) 구현
  -	UI 테스트 및 통합 테스트 추가
  -	다크 모드 / 백업 기능 지원

---

```
## 📁 프로젝트 구조
│
├── Application.kt                   # 애플리케이션의 의존성 설정 및 주입을 담당하는 메인 클래스 (콘솔용)
│
├── domain/                          1. 도메인 계층 (Domain Layer) - 가장 핵심적인 비즈니스 로직
│   ├── model/                       # 순수한 데이터 모델 (엔티티, 값 객체)
│   │   ├── Diary.kt                 # 다이어리 엔티티
│   │   └── User.kt                  # 사용자 엔티티
│   └── repository/                  # 데이터 영속성을 위한 인터페이스 (추상화)
│       ├── DiaryRepository.kt       # 다이어리 레포지토리 인터페이스
│       └── UserRepository.kt        # 사용자 레포지토리 인터페이스
│
├── application/                     2. 애플리케이션 계층 (Application Layer) - 유스케이스
│   ├── usecase/                     # 사용자의 상호작용을 바탕으로 비즈니스 흐름을 조율
│   │   ├── DiaryUseCase.kt          # 다이어리 관련 유스케이스 인터페이스
│   │   ├── DiaryUseCaseImpl.kt      # 다이어리 유스케이스 구현체
│   │   ├── UserUseCase.kt           # 사용자 관련 유스케이스 인터페이스
│   │   └── UserUseCaseImpl.kt       # 사용자 유스케이스 구현체
│   └── validator/                   # 입력값 검증 로직
│       ├── DiaryValidator.kt
│       └── InputValidator.kt
│
├── data/                            3. 데이터 계층 (Data Layer) - 데이터 영속성 구현
│   └── repository/                  # 도메인 계층의 리포지토리 인터페이스에 대한 실제 구현
│       ├── DiaryRepositoryImpl.kt   # 파일 시스템을 이용한 다이어리 레포지토리 구현체
│       └── UserRepositoryImpl.kt    # 파일 시스템을 이용한 사용자 레포지토리 구현체
│
├── presentation/                    4. 프레젠테이션 계층 (Presentation Layer) - UI 로직 (MVVM)
│   ├── view/                        # View: 사용자에게 보여지는 부분 (콘솔 입출력)
│   │   └── ConsoleView.kt
│   ├── viewmodel/                   # ViewModel: View의 상태를 관리하고 UseCase와 통신
│   │   ├── DiaryViewModel.kt
│   │   ├── DiaryViewModelImpl.kt
│   │   ├── UserViewModel.kt
│   │   └── UserViewModelImpl.kt
│   └── ConsoleMain.kt               # 애플리케이션의 시작점 및 View의 역할을 하는 메인 클래스 (콘솔용)
│
├── dto/                             # 계층 간 데이터 전송을 위한 객체
│   ├── DiaryDto.kt
│   └── UserDto.kt
│
└── port/                            # 외부 시스템과의 통신을 위한 인터페이스
    └── InputPort.kt                 # 콘솔 입력을 받기 위한 인터페이스
```

