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
    "data": [1, 3, 4, 5, 9, 7, 23, 31, 92, 43, 29, 22, 6, 9],
    "type": "jpg"
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

### Title

- Method: ?
- Path: [?]()
- Request:

```json

```

- Response:

```json

```
