# 🧩 Task Manager — веб-приложение для управления задачами

[![Actions Status](https://github.com/F-Jahura/java-project-99/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/F-Jahura/java-project-99/actions)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=F-Jahura_java-project-99&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=F-Jahura_java-project-99)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=F-Jahura_java-project-99&metric=coverage)](https://sonarcloud.io/summary/new_code?id=F-Jahura_java-project-99)


**Демо:** [deploy app](https://java-project-99-dolt.onrender.com)  
> ⚠️ *Бесплатный хостинг на Render: первый запуск может занять некоторое время.*

---

## 📋 О проекте

Task Manager — это полнофункциональное веб-приложение для управления задачами с авторизацией и разграничением прав.  
Проект написан на **Java 21** с использованием **Spring Boot**, **Spring Security (JWT)**, **Spring Data JPA**, **PostgreSQL**, **Liquibase** и **Docker**.

### Возможности

- ✅ Регистрация и аутентификация пользователей (JWT)
- ✅ CRUD операции над задачами
- ✅ Назначение исполнителей и статусов
- ✅ Фильтрация задач по статусу и автору
- ✅ Кастомные метки для задач
- ✅ Административный доступ к управлению пользователями
- ✅ Автоматическая документация API (Swagger/OpenAPI)

### Технологии

| Категория | Технологии |
|-----------|-------------|
| **Backend** | Java 21, Spring Boot 3, Spring Security, JWT, Spring Data JPA, Hibernate, Validation |
| **Database** | PostgreSQL, Liquibase, H2 (тесты) |
| **Testing** | JUnit 5, Mockito, Testcontainers |
| **Build & CI/CD** | Gradle, GitHub Actions, SonarQube, Checkstyle |
| **Container & Deploy** | Docker, Render |

---

## 🛠️ Локальный запуск

### Требования
- JDK 21
- Docker (для PostgreSQL)
- Gradle 8.7+

### Шаги

**Клонировать репозиторий:**
   ```bash
   git clone https://github.com/F-Jahura/java-project-99.git
   cd java-project-99
```

**Настроить базу данных:**  
Создайте PostgreSQL базу (можно через Docker):  
```bash
docker run --name task-manager-db -e POSTGRES_DB=taskmanager -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres:16
```

**Собрать и запустить:**
```bash
make build
make run
```

**Открыть в браузере:**
```bash
http://localhost:8080
```

**Авторизация:**
```bash

Аккаунт админа для доступа к приложению:
      
      - Логин:hexlet@example.com
      - Пароль: qwerty
```
