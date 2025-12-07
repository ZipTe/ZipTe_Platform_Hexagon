# ZipTe Platform

> 헥사고날 아키텍처 기반의 확장 가능한 부동산 플랫폼 백엔드

## 프로젝트 소개

ZipTe는 Spring Boot와 헥사고날 아키텍처(Ports and Adapters)를 적용한 부동산 플랫폼입니다.
비즈니스 로직과 인프라 관심사를 철저히 분리하여 유지보수성과 테스트 용이성을 극대화했습니다.

### 주요 특징

- **헥사고날 아키텍처**: 도메인 중심 설계로 비즈니스 로직의 독립성 보장
- **멀티 데이터베이스**: MySQL(관계형 데이터) + MongoDB(문서형 데이터) 하이브리드 구조
- **OAuth2 인증**: Google, Kakao, Naver 소셜 로그인 통합
- **AI 통합**: Gemini API를 활용한 스마트 기능
- **클라우드 스토리지**: AWS S3 기반 파일 관리
- **실시간 캐싱**: Redis 기반 세션 및 데이터 캐싱

## 기술 스택

### Backend
- **Language**: Java 17
- **Framework**: Spring Boot 3.4.4
- **Build Tool**: Gradle
- **Security**: Spring Security + JWT + OAuth2

### Database
- **MySQL**: 사용자, 매물, 리뷰 등 관계형 데이터
- **MongoDB**: 아파트 정보, 알림 등 문서형 데이터
- **Redis**: 세션 관리 및 캐싱

### Infrastructure
- **Cloud Storage**: AWS S3
- **AI Integration**: Google Gemini API
- **Documentation**: Swagger/OpenAPI

### Testing & Quality
- **Testing**: JUnit 5
- **Code Coverage**: Jacoco
- **Logging**: AOP-based Logging

## 아키텍처

### 헥사고날 아키텍처 (Ports and Adapters)

```
┌─────────────────────────────────────────────────────────┐
│                    Adapters (In)                        │
│                  REST API Controllers                   │
│              (PropertyApi, UserApi, etc.)               │
└────────────────────┬────────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────────┐
│              Application Layer                          │
│                                                          │
│  ┌──────────────────────────────────────────────────┐  │
│  │         Use Cases (In Ports)                     │  │
│  │  CreatePropertyUseCase, GetUserUseCase, etc.    │  │
│  └──────────────────┬───────────────────────────────┘  │
│                     │                                   │
│  ┌──────────────────▼───────────────────────────────┐  │
│  │              Services                            │  │
│  │  PropertyService, UserService, etc.             │  │
│  └──────────────────┬───────────────────────────────┘  │
│                     │                                   │
│  ┌──────────────────▼───────────────────────────────┐  │
│  │         Out Ports                                │  │
│  │  SavePropertyPort, LoadEstatePort, etc.         │  │
│  └──────────────────┬───────────────────────────────┘  │
└────────────────────┬────────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────────┐
│                  Adapters (Out)                         │
│  ┌─────────────┐  ┌─────────────┐  ┌────────────────┐  │
│  │ JPA Adapter │  │Mongo Adapter│  │External Adapter│  │
│  │   (MySQL)   │  │ (MongoDB)   │  │   (S3, AI)     │  │
│  └─────────────┘  └─────────────┘  └────────────────┘  │
└─────────────────────────────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────────┐
│                  Infrastructure                         │
│         MySQL, MongoDB, Redis, S3, Gemini              │
└─────────────────────────────────────────────────────────┘
```

### 계층별 역할

#### 1. 도메인 계층 (`domain/`)
- 순수 비즈니스 엔티티
- 프레임워크 의존성 제로
- 예시: `Property`, `Estate`, `User`, `Review`

#### 2. 애플리케이션 계층 (`application/`)
- **In Ports**: 유스케이스 인터페이스 정의
- **Out Ports**: 외부 의존성 인터페이스 정의
- **Services**: 비즈니스 로직 구현

#### 3. 어댑터 계층 (`adapter/`)
- **In Adapters**: REST API 컨트롤러
- **Out Adapters**:
  - JPA (MySQL 영속성)
  - MongoDB (문서 저장소)
  - External (S3, AI 통합)

## 프로젝트 구조

```
src/main/java/com/zipte/platform/
├── server/
│   ├── domain/              # 도메인 엔티티
│   │   ├── Property.java
│   │   ├── Estate.java
│   │   ├── User.java
│   │   └── ...
│   ├── application/         # 애플리케이션 계층
│   │   ├── in/             # Use Case 인터페이스
│   │   ├── out/            # Port 인터페이스
│   │   └── service/        # 비즈니스 로직
│   └── adapter/            # 어댑터 계층
│       ├── in/web/         # REST Controllers
│       └── out/
│           ├── jpa/        # MySQL 어댑터
│           ├── mongo/      # MongoDB 어댑터
│           └── external/   # 외부 서비스 어댑터
├── core/                   # 공통 설정 및 유틸리티
│   ├── config/
│   ├── exception/
│   ├── response/
│   └── security/
└── ZipTePlatformApplication.java
```

## 주요 도메인

| 도메인 | 설명 | 저장소 |
|--------|------|--------|
| **Estate** | 아파트 부동산 목록 | MongoDB |
| **Property** | 사용자 생성 매물 항목 | MySQL |
| **User** | 사용자 계정 (OAuth2) | MySQL |
| **Favorite** | 관심 매물 | MySQL |
| **Review** | 매물 리뷰 | MySQL |
| **Notification** | 알림 | MongoDB |
| **Region** | 지역 및 가격 정보 | MySQL |
| **Question** | 커뮤니티 질문 | MySQL |
| **Answer** | 커뮤니티 답변 | MySQL |

## 데이터 모델

### ERD (Entity Relationship Diagram)

```mermaid
erDiagram
    %% MySQL Entities
    User ||--o{ Property : "owns"
    User ||--o{ Review : "writes"
    User ||--o{ Favorite : "saves"
    User ||--o{ Question : "asks"
    User ||--o{ Answer : "answers"
    User ||--o{ EstateOwnership : "owns"

    Question ||--o{ Answer : "has"

    Property }o--|| Estate : "references (kaptCode)"
    Review }o--|| Estate : "reviews (kaptCode)"
    Question }o--|| Estate : "about (kaptCode)"
    Favorite }o--|| Estate : "bookmarks (kaptCode)"
    Favorite }o--|| Region : "bookmarks (regionCode)"

    Region ||--o{ RegionPrice : "has"

    %% MySQL Tables
    User {
        Long id PK
        String socialId
        String email
        String username
        String nickname
        String imageUrl
        OAuthProvider social
        UserConsent consent
        List roles
    }

    Property {
        Long id PK
        Long ownerId FK
        String kaptCode "Estate 참조"
        PropertyType type
        PropertyAddress address
        PropertySnippet snippet
        PropertyStatistic statistic
        Long price
        Boolean verified
    }

    Review {
        Long id PK
        Long userId FK
        String kaptCode "Estate 참조"
        String imageUrl
        ReviewSnippet snippet
        ReviewStatistics statistics
        Boolean certified
    }

    Favorite {
        Long id PK
        Long userId FK
        FavoriteType type
        String kaptCode "Estate 참조"
        String regionCode "Region 참조"
    }

    Question {
        Long id PK
        Long userId FK
        String kaptCode "Estate 참조"
        String title
        String content
        QuestionStatistics statistics
    }

    Answer {
        Long id PK
        Long userId FK
        Long questionId FK
        String content
    }

    Region {
        String code PK
        String address
    }

    RegionPrice {
        Long id PK
        String regionCode FK
        String date
        Long price
    }

    EstateOwnership {
        Long id PK
        Long userId FK
        String kaptCode "Estate 참조"
    }

    %% MongoDB Documents
    Estate {
        String id PK
        String kaptCode UK
        String kaptName
        String kaptAddr
        Location location "GeoJSON"
        String pricePerSquareMeter
        String convenientFacility
        String educationFacility
        String subwayStation
    }

    Notification {
        String id PK
        Long userId
        String type
        String message
        Boolean read
        DateTime createdAt
    }
```

### 데이터베이스 분리 전략

#### MySQL (관계형 데이터)
- **사용자 및 인증**: User, UserConsent, UserWeight
- **매물 거래**: Property, Review, Favorite
- **커뮤니티**: Question, Answer
- **지역 정보**: Region, RegionPrice
- **ACID 트랜잭션이 중요한 비즈니스 로직**

#### MongoDB (문서형 데이터)
- **부동산 정보**: Estate (GeoJSON 위치 정보 포함)
- **알림 시스템**: Notification (읽음/안읽음 상태 관리)
- **유연한 스키마가 필요한 데이터**
- **지리 기반 검색 (GeoSpatial Index)**

## 시퀀스 다이어그램

### 1. OAuth2 로그인 프로세스

```mermaid
sequenceDiagram
    actor User
    participant Client
    participant SecurityFilter as JWT Filter
    participant OAuth2Handler as OAuth2 Handler
    participant UserService
    participant MySQL
    participant Redis

    User->>Client: 소셜 로그인 요청
    Client->>OAuth2Handler: OAuth2 인증 시작
    OAuth2Handler->>OAuth2Handler: Provider로 리다이렉트<br/>(Google/Kakao/Naver)

    Note over OAuth2Handler: 사용자가 OAuth Provider에서 인증

    OAuth2Handler->>UserService: loadUser(OAuth2UserRequest)
    UserService->>MySQL: 사용자 조회/생성
    MySQL-->>UserService: User 엔티티

    UserService->>UserService: PrincipalDetails 생성
    UserService-->>OAuth2Handler: UserDetails

    OAuth2Handler->>OAuth2Handler: JWT 토큰 생성
    OAuth2Handler->>Redis: 토큰 저장 (TTL 설정)
    OAuth2Handler-->>Client: JWT 토큰 반환

    Client->>SecurityFilter: API 요청 (with JWT)
    SecurityFilter->>Redis: 토큰 검증
    Redis-->>SecurityFilter: 유효한 토큰
    SecurityFilter->>SecurityFilter: SecurityContext 설정
    SecurityFilter-->>Client: 인증 완료
```

### 2. 매물 등록 프로세스

```mermaid
sequenceDiagram
    actor User
    participant PropertyApi
    participant PropertyService
    participant EstateAdapter as Estate Adapter<br/>(MongoDB)
    participant PropertyAdapter as Property Adapter<br/>(MySQL)
    participant S3Adapter as S3 Adapter
    participant MongoDB
    participant MySQL

    User->>PropertyApi: POST /api/properties<br/>(매물 정보 + 이미지)
    PropertyApi->>PropertyApi: @AuthenticationPrincipal<br/>로 사용자 확인

    PropertyApi->>PropertyService: createProperty(propertyDto)

    PropertyService->>EstateAdapter: findByKaptCode(kaptCode)
    EstateAdapter->>MongoDB: Estate 조회
    MongoDB-->>EstateAdapter: Estate 정보
    EstateAdapter-->>PropertyService: Estate 도메인

    alt 이미지가 있는 경우
        PropertyService->>S3Adapter: uploadImage(file)
        S3Adapter-->>PropertyService: imageUrl
    end

    PropertyService->>PropertyService: Property 도메인 생성<br/>(비즈니스 로직 검증)

    PropertyService->>PropertyAdapter: save(property)
    PropertyAdapter->>MySQL: INSERT Property
    MySQL-->>PropertyAdapter: Saved Property
    PropertyAdapter-->>PropertyService: Property 도메인

    PropertyService-->>PropertyApi: PropertyResponse
    PropertyApi-->>User: 201 Created
```

### 3. 리뷰 작성 및 알림 프로세스

```mermaid
sequenceDiagram
    actor User
    participant ReviewApi
    participant ReviewService
    participant ReviewAdapter as Review Adapter<br/>(MySQL)
    participant NotificationAdapter as Notification Adapter<br/>(MongoDB)
    participant MySQL
    participant MongoDB

    User->>ReviewApi: POST /api/reviews<br/>(리뷰 정보)
    ReviewApi->>ReviewService: createReview(reviewDto)

    ReviewService->>ReviewService: Review 도메인 생성<br/>(평점, 내용 검증)

    ReviewService->>ReviewAdapter: save(review)
    ReviewAdapter->>MySQL: INSERT Review
    MySQL-->>ReviewAdapter: Saved Review
    ReviewAdapter-->>ReviewService: Review 도메인

    Note over ReviewService: 리뷰 작성 완료, 알림 생성

    ReviewService->>NotificationAdapter: createNotification(userId, message)
    NotificationAdapter->>MongoDB: INSERT Notification
    MongoDB-->>NotificationAdapter: Notification ID

    ReviewService-->>ReviewApi: ReviewResponse
    ReviewApi-->>User: 201 Created
```

### 4. 매물 검색 프로세스 (지리 기반)

```mermaid
sequenceDiagram
    actor User
    participant PropertyApi
    participant PropertyService
    participant EstateAdapter as Estate Adapter<br/>(MongoDB)
    participant PropertyAdapter as Property Adapter<br/>(MySQL)
    participant Redis
    participant MongoDB
    participant MySQL

    User->>PropertyApi: GET /api/properties/search<br/>?lat=37.5&lon=127.0&radius=2km

    PropertyApi->>Redis: 캐시 조회 (검색 조건 기반)

    alt 캐시 Hit
        Redis-->>PropertyApi: Cached Results
        PropertyApi-->>User: 200 OK (캐시된 데이터)
    else 캐시 Miss
        PropertyApi->>PropertyService: searchNearbyProperties(lat, lon, radius)

        PropertyService->>EstateAdapter: findNearby(location, radius)
        EstateAdapter->>MongoDB: GeoSpatial 쿼리<br/>(2dsphere index)
        MongoDB-->>EstateAdapter: Estate 목록
        EstateAdapter-->>PropertyService: Estate 도메인 목록

        PropertyService->>PropertyAdapter: findByKaptCodes(kaptCodes)
        PropertyAdapter->>MySQL: SELECT Properties
        MySQL-->>PropertyAdapter: Property 목록
        PropertyAdapter-->>PropertyService: Property 도메인 목록

        PropertyService->>PropertyService: Estate + Property 결합
        PropertyService-->>PropertyApi: PropertySearchResponse

        PropertyApi->>Redis: 검색 결과 캐싱 (TTL 5분)
        PropertyApi-->>User: 200 OK
    end
```

### 5. 커뮤니티 Q&A 프로세스

```mermaid
sequenceDiagram
    actor User1 as 질문자
    actor User2 as 답변자
    participant QuestionApi
    participant AnswerApi
    participant QuestionService
    participant AnswerService
    participant NotificationAdapter
    participant MySQL
    participant MongoDB

    User1->>QuestionApi: POST /api/questions<br/>(질문 내용)
    QuestionApi->>QuestionService: createQuestion(questionDto)
    QuestionService->>MySQL: INSERT Question
    MySQL-->>QuestionService: Question 엔티티
    QuestionService-->>QuestionApi: QuestionResponse
    QuestionApi-->>User1: 201 Created

    Note over User2: 질문 확인 후 답변 작성

    User2->>AnswerApi: POST /api/answers<br/>(답변 내용)
    AnswerApi->>AnswerService: createAnswer(answerDto, questionId)

    AnswerService->>MySQL: INSERT Answer
    MySQL-->>AnswerService: Answer 엔티티

    AnswerService->>MySQL: UPDATE Question Statistics<br/>(답변 수 증가)

    Note over AnswerService: 질문자에게 알림 전송

    AnswerService->>NotificationAdapter: createAnswerNotification(질문자ID, 답변정보)
    NotificationAdapter->>MongoDB: INSERT Notification
    MongoDB-->>NotificationAdapter: Notification ID

    AnswerService-->>AnswerApi: AnswerResponse
    AnswerApi-->>User2: 201 Created

    Note over User1: 알림 수신
    User1->>QuestionApi: GET /api/notifications
    QuestionApi->>MongoDB: 알림 조회
    MongoDB-->>QuestionApi: 알림 목록
    QuestionApi-->>User1: 새 답변 알림
```

## 시작하기

### 사전 요구사항

- Java 17 이상
- Docker & Docker Compose
- Gradle 7.x 이상

### 로컬 환경 구성

1. 저장소 클론
```bash
git clone https://github.com/ZipTe/ZipTe_Hexagon.git
cd ZipTe_Hexagon/platform
```

2. 인프라 서비스 실행
```bash
docker-compose -f compose/docker-compose.yml up -d
```

3. 애플리케이션 빌드 및 실행
```bash
# 빌드
./gradlew build

# 실행 (local 프로필)
./gradlew bootRun
```

### 테스트 실행

```bash
# 전체 테스트
./gradlew test

# 특정 테스트 클래스
./gradlew test --tests "com.zipte.platform.server.adapter.in.web.UserApiTest"

# 커버리지 리포트 생성
./gradlew jacocoTestReport

# 커버리지 검증
./gradlew jacocoTestCoverageVerification
```

## API 문서

애플리케이션 실행 후 Swagger UI에서 API 문서 확인 가능:

```
http://localhost:8080/swagger-ui.html
```

## 네이밍 컨벤션

### Port & Adapter 패턴
- Use Case: `{Action}{Entity}UseCase` (예: `CreatePropertyUseCase`)
- Port: `{Action}{Entity}Port` (예: `SavePropertyPort`)
- Service: `{Entity}Service` (예: `PropertyService`)
- Adapter: `{Entity}PersistenceAdapter` 또는 `{Purpose}Adapter`
- API: `{Entity}Api` (예: `PropertyApi`)

### 커밋 메시지 규칙
```
✨ feat/   : 새로운 기능
🐛 fix/    : 버그 수정
📖 docs/   : 문서화
✅ test/   : 테스팅
♻️ refactor/: 리팩토링
```

## 주요 기능

### 1. 인증 및 보안
- OAuth2 소셜 로그인 (Google, Kakao, Naver)
- JWT 기반 세션 관리
- Redis를 통한 토큰 저장

### 2. 매물 관리
- 매물 등록, 수정, 삭제
- 관심 매물 저장
- 매물 검색 및 필터링
- 이미지 업로드 (S3)

### 3. 리뷰 시스템
- 매물 리뷰 작성
- 평점 관리
- 리뷰 조회 및 페이지네이션

### 4. 알림
- 실시간 알림 (MongoDB)
- 알림 읽음 처리

### 5. AI 통합
- Gemini API를 활용한 스마트 기능

## 코드 품질 관리

### Jacoco 커버리지
- DTO, Request, Response, Mapper는 커버리지에서 제외
- Exception, Config 클래스 제외
- 비즈니스 로직에 집중한 커버리지 측정

### 로깅
- AOP 기반 메서드 로깅
- 실행 시간 추적
- 진입/종료 로그

### 예외 처리
- 글로벌 예외 핸들러
- 커스텀 예외 체계
- 일관된 에러 응답 형식 (`ApiResponse<T>`)

## 기술적 의사결정

### 헥사고날 아키텍처 선택 이유
1. **비즈니스 로직의 독립성**: 프레임워크나 DB 변경에 영향받지 않는 도메인 계층
2. **테스트 용이성**: 포트 인터페이스를 통한 쉬운 모킹
3. **확장성**: 새로운 어댑터 추가가 용이한 구조
4. **유지보수성**: 명확한 계층 분리로 코드 이해도 향상

### 멀티 데이터베이스 전략
- **MySQL**: ACID 특성이 중요한 트랜잭션 데이터 (매물, 사용자, 리뷰)
- **MongoDB**: 유연한 스키마가 필요한 데이터 (아파트 정보, 알림)
- **Redis**: 빠른 응답이 필요한 세션 및 캐시

## 라이선스

This project is licensed under the MIT License.
