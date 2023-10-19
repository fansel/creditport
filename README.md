# swtp-2023-13

## API Routes Übersicht

| Methods | Urls              | Actions              |
|---------|------------------ |------------------    |
| POST    | /api/users/login  | login an account     |
| POST    | /api/users/signup | signup new account   |
| GET     | /api/procedures   | Gibt alle Vorgänge zurück   |


## API Routes Dokumentation

### /api/users/login

**Request**
```json
{
  "username": "",
  "password_hash" : "",
}
```

**Respond**
```json
{
  "token": "", //JWT Token
}
```

### /api/users/signup

**Request**
```json
{
  "username": "",
  "password_hash" : "",
  "role" : "",
}
```

**Respond**
```json
{
  "status": true,
  "token": "", //JWT Token
}
```

### /api/procedures

**Respond**
```json
{
  [
    { "" },
  ]
}
```