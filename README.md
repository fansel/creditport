# swtp-2023-13

## API Routes Übersicht

| Methods | Urls              | Actions              |
|---------|------------------ |------------------    |
| POST    | /api/users/login  | login an account     |
| POST    | /api/users/signup | signup new account   |
| GET     | /api/procedures   | Gibt alle Vorgänge zurück   |
| POST    | /api/procedure    | Erstellt einen neuen Vorgang   |
| GET     | /api/modules | Gibt alle internen Module der Fakultät Informatik zurück |




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
  "data": 
  [
    { 
      "createdAt" : "DateTime",
      "university" : "String",
      "course" : "String",
      "status" : "Int", //Muss an anderer Stelle noch spezifiziert werden
      "requestCount" : "Int",
     },
  ],
  "count": "Int", //Anzahl der Vorgänge die zurückgegeben
}
```

### /api/procedure

**Request**
```json
{
  "university": "String",
  "course" : "String",
  "requests" : [
    {
      "externalModules" :
      [
        {
          "lp" : "Int",
          "name" : "String",
          "urlModulDescription" : "String",
          "fileModulDescription" : "File",
        },

      ],
      "internalModules" : [

      ]
    },
  ],
}
```

**Respond**
```json
{
  "vorgangsnummer": "String", 
  "success": "Boolean",
  "pdfLink" : "String",
}
```

### /api/modules

**Respond**
```json
{
  "modules" : [
    {
      "name" : "String",
      "number" : "String",
    }
  ]
}