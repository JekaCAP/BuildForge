# BuildForge

BuildForge — платформа для создания, тестирования и анализа игровых сборок.  
Цель: предоставить пользователям возможность создавать сборки персонажей в разных играх, комбинировать предметы и просматривать результаты.

- [**Архитектура приложения и используемые паттерны**](https://excalidraw.com/#json=G8_KVZF2kVHoN004veOQi,IDNcHA1AFvNVMRmJArSOQg) - нажми на меня и всё увидишь.

---

## Ключевые принципы

- Микросервисная архитектура
- Распределённые базы данных
- Event-driven интеграция через Kafka
- API First подход с генерацией DTO и клиентов

---

## Архитектура микросервисов

### Kubernetes Cluster

├─ API Gateway (Ingress Controller + Cakewalk)
│ ├─ Проверка JWT/OAuth2
│ ├─ Балансировка нагрузки
│ └─ Маршрутизация REST/gRPC
│
├─ User Service
│ ├─ PostgreSQL: user_db
│ ├─ Таблицы: users, profiles, roles
│ ├─ S3: хранение аватаров
│ └─ Kafka: UserCreated, UserUpdated
│
├─ Game Service
│ ├─ PostgreSQL: game_db
│ ├─ Таблицы: games, items, item_attributes
│ └─ REST/gRPC для Build Service
│
├─ Build Service
│ ├─ PostgreSQL: build_db
│ ├─ Таблицы: builds, build_items
│ └─ Kafka: BuildCreated, BuildUpdated
│
├─ Account Service (опционально)
│ ├─ PostgreSQL: account_db
│ └─ Kafka: PaymentProcessed
│
├─ File Service
│ └─ S3 для файлов (аватары, изображения предметов)
│
├─ Observability
│ ├─ Prometheus + Micrometer
│ ├─ Grafana dashboards
│ ├─ ELK Stack для логов
│ └─ OpenTelemetry для трассировки
│
└─ Kafka Cluster
└─ Асинхронная интеграция между сервисами

---

## Базы данных

- Каждый сервис имеет свою БД для изоляции и независимого масштабирования.
- Используется PostgreSQL + Flyway для миграций.
- Возможна генерализация для read-only реплик для аналитики.

---

## API & DTO

- OpenAPI / TypeSpec для генерации DTO и фронтенд-клиентов.
- Пример Build API:

```json
GET /builds/{gameId}/{buildId}
Response:
{
  "id": "uuid",
  "gameId": "dark-souls-3",
  "name": "Strength Build",
  "items": [
    {
      "id": "001",
      "category": "Weapon",
      "name": "Great Sword",
      "attributes": {
        "damage": 100,
        "weight": 12
      }
    }
  ]
}
```
- Гибкая структура items.attributes позволяет поддерживать разные игры и категории предметов.

---

## Security

- **Cakewalk** для аутентификации и авторизации на Gateway
- **JWT / OAuth2**, RBAC для фронта и микросервисов
- **mTLS** между микросервисами (по желанию)

---

## Observability & Metrics

- **Backend**: latency, throughput, ошибки, Kafka lag → Prometheus → Grafana
- **Frontend**: RUM, ошибки JS → Sentry / OpenTelemetry JS → Grafana
- **Логи**: централизованно в ELK Stack

---

## Асинхронная интеграция

**Kafka события**:

- `UserCreated`, `UserUpdated`
- `BuildCreated`, `BuildUpdated`
- `PaymentProcessed`

Позволяет микросервисам оставаться слабо связанными.

---

## Production Patterns

- **Circuit Breaker / Retry** для устойчивости
- **Feature Flags** для включения/отключения фич
- **Distributed Tracing** для end-to-end запросов
- **Caching layer (Redis)** для frequently used data
- **Health Checks / Liveness & Readiness Probes**

