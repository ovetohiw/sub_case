Для старта достаточно использовать /docker/start.sh. Он создает образ докер со всем 
необходимым окружением. База данных PostgreSQL.

Также создал собственный фильтр для ServletRequest, чтобы фильтровать повторяющиеся запрос.
Достаточно добавить в параметр запроса UUID (?UUID=9fdff129-bc4e-46a3-96d4-21089f5f7c1d).

В бд есть некотрое количество данных для теста:
```sql
insert into sys_subscribe(id, name)
values (1, 'Яндекс.Плюс'),
       (2, 'VK Музыка'),
       (3, 'Netflix'),
       (4, 'YouTube Premium'),
       (5, 'Twitch');

insert into sys_user (name, email)
values ('Ivan', 'ivan.id@yandex.ru');

insert into sys_subscribers (user_id, subscribe_id)
values (1, 1),
       (1, 2),
       (1, 5);
```

Контроллер апи пользователя:
---

#### `POST` /user/create - создание пользователя

**Request:**
```json5
{
    "user" : {
        "name": "name",
        "email": "mail@mail.ru"
    },
    "uuid" : "9fdff129-bc4e-46a3-96d4-21089f5f7c1d"
}
```
**Response:**
```json5
{
  "uuid": "9fdff129-bc4e-46a3-96d4-21089f5f7c1d",
  "message": "Save successful"
}
```
---

#### `GET` /user/find/{user_id} - получение пользователя по id

**Response:**
```json5
{
  "id": 1,
  "name": "Ivan",
  "email": "ivan.id@yandex.ru"
}

```

---

#### `PUT` /user/update - изменение пользователя

**Request:**
```json5
{
    "user" : {
        "id" : 1,
        "name": "name",
        "email": "mail@mail.ru"
    },
    "uuid" : "9fdff129-bc4e-46a3-96d4-21089f5f7c1d"
}
```

**Response:**
```json5
{
    "uuid": "9fdff129-bc4e-46a3-96d4-21089f5f7c1d",
    "message": "Update successful"
}
```

---

#### `DELETE` /user/delete - удаление пользователя

**Request:**
```json5
{
    "userId" : 1,
    "uuid" : "9fdff129-bc4e-46a3-96d4-21089f5f7c1d"
}
```

**Response:**

```json5
{
    "uuid": "9fdff129-bc4e-46a3-96d4-21089f5f7c1d",
    "message": "Delete successful"
}
```

---

Контроллер апи подписок:
---

#### `POST` /sub/add/subscription - добавление подписки пользователю

**Request:**
```json5
{
    "subscriptionId" : 1,
    "userId" : 1,
    "uuid" : "9fdff129-bc4e-46a3-96d4-21089f5f7c1d"
}
```
**Response:**
```json5
{
    "uuid": "9fdff129-bc4e-46a3-96d4-21089f5f7c1d",
    "message": "Save successful"
}
```

---

#### `GET` /sub/find/{user_id} - получение подписок пользователя

**Response:**
```json5
[
    "Яндекс.Плюс",
    "VK Музыка",
    "YouTube Premium",
    "Twitch"
]
```

---

#### `GET` /sub/find/{user_id}/missing-subscriptions - получение неактивных подписок пользователя

**Response:**
```json5
{
    "3": "Netflix"
}
```

---

#### `DELETE` /sub/delete/subscription - удаление подписки пользователя

**Request:**
```json5
{
    "subscriptionId" : 1,
    "userId" : 1,
    "uuid" : "9fdff129-bc4e-46a3-96d4-21089f5f7c1d"
}
```
**Response:**
```json5
{
    "uuid": "9fdff129-bc4e-46a3-96d4-21089f5f7c1d",
    "message": "Delete successful"
}
```
---

#### `GET` /sub/findTopic - получение ТОП-3 подписок по популярности

**Response:**
```json5
[
    "Twitch",
    "YouTube Premium",
    "VK Музыка"
]
```
