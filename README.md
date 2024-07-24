# Проект "File-store API"

## Оглавление
- [Стек](#стек)
- [Описание проекта](#описание-проекта)
- [Основные endpoints](#основные-endpoints)
- [Примеры запросов](#примеры-запросов)
- [Покрытие кода тестами](#покрытие-кода-тестами)
- [Инструкция по запуску](#инструкция-по-запуску)
  - [С помощью Docker](#с-помощью-docker)
  - [Через IDE (для IDEA)](#через-ide-для-idea)

## Стек
- Java 17
- Spring (Boot, Web, JPA)
- PostgreSQL
- RestAssuredMockMvc
- Mapstruct

## Описание проекта

Данный сервис выполняет роль хранилища различных файлов и их атрибутов. Его функционал:
- Добавление файла
- Получение файла по id 
- Получение страницы с файлами с сортировкой по дате создания 

Весь код контроллера и сервиса был покрыт unit-тестами.


## Основные endpoints

- `POST /api/v1/files` -> создание файла (принимает тело в формате JSON)
    ```json
    {
        "file": "string",
        "title": "string",
        "description": "string",
        "creationTime": "localDate"
    }
    ```
    Все поля за исключением `description` обязательны.

- `GET /api/v1/files/{id}` -> получение файла по id (возвращает тело в формате JSON)
    ```json
    {
        "file": "string",
        "title": "string",
        "description": "string",
        "creationTime": "localDate"
    }
    ```

- `GET /api/v1/files/` -> получение страницы с файлами, дополнительно принимает параметры page(int), size(int), direction(string). Для direction допустимы только два значения - asc/desc

## Примеры запросов

### POST /api/v1/files -> создание файла

![image](https://github.com/user-attachments/assets/82c6005a-4140-4b41-a9ab-0aa7e4761d66)

![image](https://github.com/user-attachments/assets/70c78f4f-7b67-43e2-964f-135736b4eaca)


### GET /api/v1/files/{id} -> получение файла по id

![image](https://github.com/user-attachments/assets/d6d71522-d1cd-4b54-8b9b-d34f010f01ab)

![image](https://github.com/user-attachments/assets/478ff90a-1891-4093-9f9c-16dc9b3782d7)


### GET /api/v1/files/ -> получение страницы с файлами

![image](https://github.com/user-attachments/assets/4eded317-0dde-4312-9375-8694858808e0)

![image](https://github.com/user-attachments/assets/6d85d0d2-a266-4043-812e-00c61f0acf5d)

![image](https://github.com/user-attachments/assets/6608f987-65fc-4a92-b7d7-eb2d86954376)


## Покрытие кода тестами

![image](https://github.com/user-attachments/assets/7c4c4c6f-c1c6-4fca-ad0a-d10b0ef1cf36)


## Инструкция по запуску

**Важно**: внешние порты, используемые в docker-compose файлах могут быть заняты на вашей системе. Для исправления необходимо в ручную установить свободный порт в используемом docker-compose. 

В docker-compose.yml можно просто изменить порт у сервиса app (напр., "8001:8080" -> "8222":"8080")

В docker-compose-dev.yml при изменении порта базы данных необходимо изменить порт в main/resources/application-dev.yml (spring.datasource.url)

### С помощью Docker
1. Клонировать репозиторий:
    ```sh
    git clone https://github.com/ryzendee/filestore-api
    ```
2. Перейти в директорию приложения:
    ```sh
    cd filestore-api
    ```
3. Запустить docker-compose (по желанию добавить -d для запуска в фоновом режиме):
    ```sh
    docker compose up
    ```

### Через IDE (для IDEA)
1. Клонировать репозиторий:
    ```sh
    git clone https://github.com/ryzendee/filestore-api
    ```
2. Перейти в директорию приложения:
    ```sh
    cd filestore-api
    ```    
2. Запустить docker-compose-dev (по желанию добавить -d для запуска в фоновом режиме):
    ```sh
    docker-compose -f docker-compose-dev.yml up
    ```
3. В edit configurations для приложения задать Active Profile как `dev`. Далее приложение можно запускать через главный класс.
![image](https://github.com/user-attachments/assets/038c6247-86bc-4495-a9b0-31f7c617b65b)

