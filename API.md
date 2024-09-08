# API Document
### End Point: http://localhost:8080

---
### Register
+ Method: POST
+ Path: [/user-manager/register]()
+ Request:
```json
{
  "username": "username",
  "password": "123456",
  "fullName": "Nguyen Van A",
  "dateOfBirth": "1999-01-15T00:00:00.000Z",
  "sex": "Male"
  }
```
+ Response:
```json
{
    "error": "SUCCESS",
    "data": null
}
```

---
### Login
+ Method: POST
+ Path: [/user-manager/login]()
+ Request:
```json
{
    "username": "username",
    "password": "123456"
}
```
+ Response:
```json
{
    "error": "SUCCESS",
    "data": {
        "nickName": "Nguyen Van A",
        "avatar": "",
        "token": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIyMDI0MDkxOCIsImlhdCI6MTcyNTc2OTcwNCwiZXhwIjoxNzI1ODU2MTA0fQ.79FZQEE2LpdSx7_IQkSN5nxQ7DsfjZ99YvWCw9hNqTtiFeyizXTAjl_QQnlaj_Bq"
    }
}
```