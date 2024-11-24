# refactor-backend

### 실행 방법
- [프론트] : src > main > resources > static > index.html 파일 실행
- [백엔드] : src > main > java > com.refactor.backend.BackendApplication 실행

## 프론트 연결 API

### 파일 생성

```javascript
[POST] ${host}/directories/${userName}/files
// Request form-data
{
    "file": file,
    "filename": string
}

// 201 Created
```

### 파일구조 가져오기

```javascript
[GET] ${host}/directories
// 200 OK
[
    [
        {
            "userName": string,
            "filename": string
        },
        {
            "userName": string,
            "filename": string
        }
    ],
    [
        {
            "userName": string,
            "filename": string
        }
    ]
]
```

### 파일 리소스 가져오기

```javascript
[GET] ${host}/directories/${folderName}/files/${filename}

// 200 OK
```

### 파일 리소스 다운로드

```javascript
[GET] ${host}/directories/${folderName}/files/${filename}/download

// 200 OK
```

### 파일 리소스 삭제 

```javascript
[DELETE] ${host}/directories/${folderName}/files/${filename}

// 204 No Content
```
