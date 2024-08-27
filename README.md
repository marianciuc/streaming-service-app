# Стриминговый сервис

Это backend-приложение на основе микросервисной архитектуры, предназначенное для предоставления услуг стриминга видео по подписке. Система управления подписками интегрирована с платежным шлюзом Stripe, что позволяет обрабатывать платежи и управлять статусами подписок. Каждый день в полночь сервис подписок проверяет подписки, срок которых истекает, и автоматически продлевает их или аннулирует в зависимости от настроек пользователя и статуса платежа.

## Установка
Для установки проекта на вашу локальную машину выполните следующие шаги:

1. Клонируйте репозиторий:
```bash
git clone https://github.com/your-username/streaming-service.git
```
2. Перейдите в каталог проекта:
```bash
cd streaming-service
```
3. Соберите проект и установите зависимости:
```bash
./mvnw clean install
```

## Использование
### Запуск в Docker
Для запуска всех сервисов в контейнерах Docker выполните следующую команду:
```bash
docker compose up -d
```

### Запуск сервисов отдельно
Если вы хотите запустить каждый микросервис отдельно, перейдите в соответствующий каталог в `services` и выполните команду:
```bash
./mvnw spring-boot:run
```

## Стек технологий
Проект использует следующие технологии:
- Java JDK 22
- JWT
- Spring Boot: фреймворк для создания микросервисов.
- Spring Security: для управления аутентификацией и авторизацией.
- Feign: декларативный HTTP клиент для микросервисов.
- Spring Data JPA: для работы с реляционными базами данных.
- PostgreSQL: реляционная база данных для хранения основной информации.
- MongoDB: NoSQL база данных для хранения данных, не требующих жесткой схемы.
- Eureka: сервис регистрации для управления микросервисами.
- Spring Cloud Config: для централизованного управления конфигурацией.
- Flyway Migration: для управления версионированием базы данных.
- Zookeeper: для координации распределенных систем.
- Gateway: API-шлюз для маршрутизации запросов.
- Quartz: планировщик задач для управления периодическими операциями.
- Stripe: интеграция с платежной системой для управления подписками и платежами.
## Диаграммы
Все диаграммы использования и архитектуры, связанные с проектом, находятся в папке `diagrams`. Эта папка содержит визуализации, которые помогут лучше понять структуру и взаимодействие компонентов системы.

## Лицензия

Этот проект распространяется под лицензией MIT. Подробности см. в файле LICENSE в корневом каталоге проекта.