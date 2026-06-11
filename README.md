<img width="1920" height="1080" alt="togedy2" src="https://github.com/user-attachments/assets/e9f1ea4d-8cf5-4c82-ab71-11664b4410d0" />

<h1 align="center">
  <img width="72" height="72" alt="togedy_logo" src="https://github.com/user-attachments/assets/73d6678d-f4f2-4bcd-94ef-b2442e142c5e" /> Togedy</h1>

<p align="center">
  <b>Together + Study</b> — 입시생과 함께 공부하고, 입시준비를 도와주는 서비스
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Android-3DDC84?style=flat-square&logo=android&logoColor=white"/>
  <img src="https://img.shields.io/badge/Kotlin-7F52FF?style=flat-square&logo=kotlin&logoColor=white"/>
  <img src="https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=flat-square&logo=jetpackcompose&logoColor=white"/>
  <img src="https://img.shields.io/badge/Clean%20Architecture-black?style=flat-square"/>
  <img src="https://img.shields.io/badge/Multi--Module-orange?style=flat-square"/>
</p>

---

## 📖 소개

**Togedy**는 대학입시를 준비하는 모든 수험생들을 위해 입시 정보를 빠르고 간단하게 찾을 수 있도록 도와주는 서비스입니다 🙂

정보 검색뿐만 아니라, 일정 관리를 위한 커스터마이징 가능한 입시 캘린더와 공부에 집중할 수 있도록 스터디 플래너, 시간 기록 등의 다양한 기능을 제공합니다.

---

## ✨ 주요 기능

### 🤖 입시 GPT
입시 정보(모집기간, 전형 등)를 채팅 형식으로 빠르고 정확하게 얻을 수 있는 AI 챗봇 기능

### 👥 스터디
다른 사람과 스터디를 만들어 함께 공부할 수 있는 기능
- 스터디원 간 공부량 공유
- **챌린지 스터디** : 목표 시간을 설정하여 스터디원들이 함께 공부 챌린지 진행

### 📅 캘린더
- 개인 일정 관리
- 관심 대학의 입시 일정을 내 캘린더에 직접 저장 가능

### 📝 스터디 플래너
- 공부 시간 기록 (열품타 스타일)
- 데일리 스터디 플랜 작성 및 상태 관리

---

## 🛠 기술 스택

### Architecture & Pattern
| 항목 | 내용 |
|------|------|
| Architecture | Clean Architecture |
| Presentation Pattern | MVVM + MVI |
| Module System | Multi-Module |

### Android
| 항목 | 내용 |
|------|------|
| UI | Jetpack Compose |
| Navigation | Navigation Compose |
| DI | Hilt |
| Async | Kotlin Coroutines + Flow |
| Network | Retrofit2 + OkHttp3 |
| Local DB | Room |
| Image Loading | Coil |
| Build System | Gradle Version Catalog + Convention Plugin |

---

## 🏗 모듈 구조
```
Togedy_Android_V2
├── app                  # 앱 진입점, Application, MainActivity
├── build-logic          # Convention Plugin (Gradle 빌드 설정 공통화)
├── core
│   ├── common           # 공통 유틸리티
│   ├── designsystem     # 공통 UI 컴포넌트, 테마
│   ├── network          # 네트워크 설정 (Retrofit, Interceptor)
│   └── data             # 공통 데이터 처리
├── domain               # UseCase, Repository Interface, Model
├── data                 # Repository 구현체, API, DAO
└── presentation
├── home
├── gpt              # 입시 GPT
├── study            # 스터디
├── calendar         # 캘린더
└── planner          # 스터디 플래너
```

---

## 👩‍💻 팀원

| 이름  | GitHub                                     | 담당 기능                           |
|-----|--------------------------------------------|---------------------------------|
| 김채린 | [김채린](https://github.com/chrin05)        | 캘린더 메인, 플래너, 스터디 상세, 마이페이지      |
| 김태정 | [김태정](https://github.com/imtaejugkim)        | 카카오 로그인, 대학 캘린더, 입시 GPT, 스터디 탐색 |

---

<p align="center">
  Made by Togedy Team
</p>
