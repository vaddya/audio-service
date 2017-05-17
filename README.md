# Music-service

## Задачи

1. Прослушивание музыки
2. Создание плейлистов
3. Рекомендации
4. Обновления друзей
5. Поиск песен
    * по исполнителю
    * по названию
    * по жанру
    * по году

## Взаимодействие со сторонними сервисами

1. Авторизация
2. Пользователи
3. Счётчики числа прослушивания песен 

## Хранение данных

* Бинарные файлы - на жёстких дисках (хранилища типа amazon s3)
* Информация о песнях - в NoSQL базе данных (возможно MongoDB)
* Кэш

## Технологии
   * REST ful service

## API
Решено сделать restful сервис, поэтому rest api:

* Поиск песен

   `GET /search?name=NAME&artist=ARTIST&genre=GENRE&year=2018`

* Создать плейлист

   `POST /playlists`

   BODY: `{"name": "name", "playlistId": "a568b9c512febeaf64782", "songs": [{"songId" : "someId"} ...] }`
   
* Обновить плейлист
   
   `PUT /playlists/{playlistId}`
   
   BODY: `[{"songId" : "someId"} ...]`
   
* Получить песню

* Получить плейлист
   
* TODO
