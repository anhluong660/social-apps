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
- Method: GET
- Path: [/user-manager/user-info]()
- Response:
```json
{
  "userId": 1,
  "nickName": "Nguyen Van A",
  "avatar": "",
  "dateOfBirth": "1999-09-07T00:00:00Z",
  "sex": "Male"
}
```

---

### Get User Info List
- Method: POST
- Path: [/user-manager/user-info-list]()
- Request:
```json
  [1, 2]
```
- Response:
```json
{
  "error": "SUCCESS",
  "data": [
    {
      "userId": 1,
      "nickName": "Nguyen Van A",
      "avatar": "",
      "dateOfBirth": "1999-01-01T00:00:00Z",
      "sex": "Male"
    },
    {
      "userId": 2,
      "nickName": "Tran Van B",
      "avatar": "",
      "dateOfBirth": "1999-01-01T00:00:00Z",
      "sex": "Male"
    }
  ]
}
```

---

### Add Friend
- Method: POST
- Path: [/user-manager/add-friend/{friendId}]()
- Response:
```json
{
    "error": "SUCCESS",
    "data": null
}
```

---

### Remove Friend
- Method: POST
- Path: [/user-manager/remove-friend/{friendId}]()
- Response:
```json
{
    "error": "SUCCESS",
    "data": null
}
```

---

### Get All Friend
- Method: GET
- Path: [/user-manager/friends]()
- Response:
```json
{
    "error": "SUCCESS",
    "data": [
        {
            "userId": 1,
            "nickName": "Nguyen Van A",
            "avatarUrl": "",
            "online": true
        }
    ]
}
```

---

### Get All Inviter
- Method: GET
- Path: [/user-manager/inviters]()
- Response:
```json
{
    "error": "SUCCESS",
    "data": [
        {
            "userId": 1,
            "nickName": "Nguyen Van A",
            "avatarUrl": ""
        }
    ]
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

### Get My Post List
- Method: GET
- Path: [/social-service/my-post-list/{userId}]()
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
            "authorId": 123,
            "authorName": "Nguyen Van A",
            "authorAvatar": "avatar.png",
            "createTime": "2024-09-11T04:19:05Z",
            "postType": "IMAGE",
            "content": "This is my post",
            "mediaLink": "http://image.jpg",
            "numLikes": 100,
            "numComments": 10
        }
    ]
}
```
---

### Get Post List
- Method: GET
- Path: [/social-service/post-list?type=IMAGE&page=1]()
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
            "authorId": 123,
            "authorName": "Nguyen Van A",
            "authorAvatar": "avatar.png",
            "createTime": "2024-09-11T04:19:05Z",
            "postType": "IMAGE",
            "content": "This is my post",
            "mediaLink": "http://image.jpg",
            "numLikes": 100,
            "numComments": 10
        }
    ]
}
```
---

### Like Post
- Method: POST
- Path: [/social-service/like-post]()
- Request:
```json
{
    "postId": 1
}
```
- Response:
```json
{
    "error": "SUCCESS",
    "data": {
        "isLiked": true
    }
}
```

---

### Post Comment
- Method: POST
- Path: [/social-service/comment]()
- Request:
```json
{
    "postId": 1,
    "content": "Hello World"
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

### Get All Comment Of Post
- Method: GET
- Path: [/social-service/comments/1]()
- Response:
```json
{
  "error": "SUCCESS",
  "data": [
    {
      "commentId": 6,
      "authorId": 3,
      "authorName": "Nguyen Van A",
      "authorAvatar": "",
      "content": "Haha"
    },
    {
      "commentId": 8,
      "authorId": 4,
      "authorName": "Tran Van B",
      "authorAvatar": "",
      "content": "Very Good !"
    }
  ]
}
```

---

### Get Chat Box
- Method: GET
- Path: [/messenger/chat-box?chatBoxId=2_1&page=1]()
- Response:
```json
{
    "error": "SUCCESS",
    "currentPage": 1,
    "pageSize": 2,
    "total": 7,
    "list": [
        {
            "authorId": 1,
            "type": "TEXT",
            "content": "Message Content 1",
            "createTime": "2024-09-19T06:29:15.724+00:00"
        },
        {
            "authorId": 2,
            "type": "TEXT",
            "content": "Message Content 2",
            "createTime": "2024-09-19T06:29:21.467+00:00"
        }
    ]
}
```

---

### Web Socket Chat
- Method: WebSocket
- Path: [ws://localhost:8080/messenger/websocket/chat]()
- Request:
```json
{
    "code": "CHAT_USER",
    "chatBoxId": "abc_123",
    "receiverId": 2,
    "messageType": "TEXT",
    "content": "Hello World"
}
```
- Response:
```json
{
    "code": "CHAT_USER",
    "chatBoxId": "abc_123",
    "senderId": 3,
    "messageType": "TEXT",
    "content": "Hello World"
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