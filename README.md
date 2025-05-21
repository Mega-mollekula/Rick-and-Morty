# Rick and Morty App 🧪🪐

Приложение на Android с использованием архитектуры Clean Architecture, Jetpack Compose и Ktor, которое отображает персонажей из вселенной Rick and Morty.

---

## 🏗 Архитектурные слои

Проект структурирован по архитектурному подходу с разделением на независимые слои:

### 📁 Domain Layer (`domain/`)
Слой бизнес-логики и основной модели данных:
- `CharacterEntity` — доменная модель персонажа.
- `IRickRepository` — интерфейс репозитория.
- Не зависит от других слоёв.

---

### 📁 Data Layer (`data/`)
Отвечает за работу с данными:
- `RickRepository` — реализация `IRickRepository`.
- DTO: `CharacterDTO`, `CharacterListDTO` — модели для сети.
- Room-модели: `CharacterModel`.
- DAO: `CharactersDAO` — доступ к локальной БД.
- Мапперы: преобразование между слоями.
- API: `RickApiService` — HTTP-запросы через Ktor.

---

### 📁 Presentation Layer (`presentation/`)
Отвечает за UI и пользовательское взаимодействие:
- `CharacterViewModel` — ViewModel с логикой отображения.
- `CharacterScreen` — UI, построенный на Jetpack Compose.
- `UiState` — sealed-класс для состояния интерфейса (`Loading`, `Error`, `Empty`, `Success`).

---

### ⚙️ Common Layer (`common/`)
Слой общих зависимостей:
- `NetworkModule` — настройка клиента Ktor.
- `DatabaseModule` — инициализация Room.
- `AppDatabase` — база данных.

---

## 🚀 Технологии

- **Kotlin**
- **Jetpack Compose** — UI
- **Kotlin Coroutines / Flow** — асинхронность и реактивность
- **Room** — локальное хранилище
- **Ktor Client** — HTTP-запросы
- **Kotlinx Serialization** — JSON-парсинг
- **Material 3** — UI-компоненты

---

## 🌟 Особенности

- ✅ Пагинация: постраничная загрузка персонажей (по 20 на странице)
- ✅ Кэширование: локальное сохранение данных с офлайн-доступом
- ✅ Обработка ошибок и состояний: `Loading`, `Error`, `Success`, `Empty`
- ✅ Использование чистой архитектуры с разделением ответственности
