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
│   ├─ PostgreSQL: user_db
│   ├─ Таблицы: users, profiles, roles (аватары могут храниться в base64 на раннем этапе)
│   ├─ S3 (планируется для хранения файлов при масштабировании)
│   └─ Kafka: UserCreated, UserUpdated (события слушают Build и Account сервисы для синхронизации профилей и владельцев билдов)
│
├─ Game Service
│ ├─ PostgreSQL: game_db
│ ├─ Таблицы: games, items, item_attributes
│ └─ REST/gRPC для Build Service
│
├─ Build Service
│   ├─ PostgreSQL: build_db
│   ├─ Таблицы: builds, build_items
│   └─ Kafka: BuildCreated, BuildUpdated
│
│   ─ Примечание: на MVP этапе возможно объединение Build и Game сервисов в один модуль
│   ─ для упрощения разработки и согласованной модели данных.
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

- На старте используется **единый экземпляр PostgreSQL** с разделением по схемам (`user_schema`, `game_schema`, `build_schema`)
для упрощения деплоя и настройки CI/CD.  
- В дальнейшем возможен переход на **отдельные инстансы** для независимого масштабирования и отказоустойчивости.

---

## API & DTO

- OpenAPI / TypeSpec спецификации хранятся в директории `/api-specs` монорепозитория  
- и используются для автогенерации DTO и клиентов (через Gradle task или CI pipeline).

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
- Гибкость структуры `items.attributes` заключается в возможности хранить разные наборы атрибутов для разных игр.  
  Например, в Dark Souls предмет имеет `{damage, weight}`, а в Diablo — `{attack_speed, rarity, socket_count}`.

---

## Security

- **Keycloak** для аутентификации и авторизации на Gateway
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

