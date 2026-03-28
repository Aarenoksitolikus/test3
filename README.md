## CRUD-операции

### Получить список записок c пагинацией (по умолчанию 20 штук на странице):

GET /api/v1/notes

### Получить все записки, у которых есть строка в title:

GET /api/v1/notes/match?titleContains={строка}

### Получить все записки, у которых есть строка в content:

GET /api/v1/notes/match?contentContains={строка}

### Получить все записки, у которых есть строка1 в title или строка2 в content

GET /api/v1/notes/match?titleContains={строка1}&contentContains={строка2}

#### (Подробнее можно посмотреть на localhost:8080/openapi)
