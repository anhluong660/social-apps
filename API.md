# API Document

### End Point: http://localhost:8080

---

### Register

- Method: POST
- Path: [/user-manager/register]()
- Request:

```json
{
  "username": "username",
  "password": "123456",
  "fullName": "Nguyen Van A",
  "dateOfBirth": "1999-01-15T00:00:00.000Z",
  "sex": "Male"
}
```

- Response:

```json
{
  "error": "SUCCESS",
  "data": null
}
```

---

### Login

- Method: POST
- Path: [/user-manager/login]()
- Request:

```json
{
  "username": "username",
  "password": "123456"
}
```

- Response:

```json
{
  "error": "SUCCESS",
  "data": {
    "token": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIyMDI0MDkxOCIsImlhdCI6MTcyNTc2OTcwNCwiZXhwIjoxNzI1ODU2MTA0fQ.79FZQEE2LpdSx7_IQkSN5nxQ7DsfjZ99YvWCw9hNqTtiFeyizXTAjl_QQnlaj_Bq"
  }
}
```

---

### Get User Info

- Method: POST
- Path: [/user-manager/user-info]()
- Response:

```json
{
  "nickName": "Nguyen Van A",
  "avatar": "",
  "dateOfBirth": "1999-09-07T00:00:00Z",
  "sex": "Male"
}
```

---

### Upload File

- Method: POST
- Path: [http://localhost:9100/upload]()
- Request:

```json
{
    "header": {
      "Authorization": "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIyMDI0MDkxOCIsImlhdCI6MTcyNTc2OTcwNCwiZXhwIjoxNzI1ODU2MTA0fQ.79FZQEE2LpdSx7_IQkSN5nxQ7DsfjZ99YvWCw9hNqTtiFeyizXTAjl_QQnlaj_Bq",
      "Content-Type": "image/jpg"
    },
    "body": [82, 3, 4, 2, 34, 56, 21, 39, 45, 98, 64, 28, 1, 28, 23, 12, 34, 89, 86, 56]
}
```

- Response:

```json
{
  "error": "SUCCESS",
  "data": {
    "url": "https://cloud.service/d801cb81-a185-4786-8f01-9388b3db2518.jpg"
  }
}
```

---

### New Post

- Method: POST
- Path: [/social-service/new-post]()
- Request:

```json
{
    "content": "This is my post",
    "mediaLink": "http://image.jpg"
}
```

- Response:

```json
{
    "error": "SUCCESS",
    "data": null
}
```

---

### Title

- Method: GET
- Path: [/social-service/my-post-list]()
- Response:

```json
{
    "error": "SUCCESS",
    "currentPage": 1,
    "pageSize": 1,
    "total": 1,
    "list": [
        {
            "postId": 1,
            "authorName": "Nguyen Van A",
            "authorAvatar": "avatar.png",
            "createTime": "2024-09-11T04:19:05Z",
            "content": "This is my post",
            "mediaLink": "http://image.jpg",
            "numLikes": 100,
            "numComments": 10
        }
    ]
}
```
---

### Title

- Method: ?
- Path: [?]()
- Request:

```json

```

- Response:

```json

```