# swtp-2023-13

## API Routes Übersicht

| Methods | Urls              | Actions              |
|---------|------------------ |------------------    |
| POST    | /api/users/login  | login an account     |
| POST    | /api/users/signup | signup new account   |


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
  "token": "",
}
```