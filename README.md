# Url-Shortener-Service
A URL shortening service with a RESTful API. This service should allow users to submit long URLs and receive short URLs that redirect to the original long URLs.
<img width="1349" alt="ui" src="https://github.com/AdrianaChuu/Url-Shortener-Service/assets/134931782/938a7d4a-804a-47f7-8953-2007ce827a0c">

Containing the following components:

WebClient Test

Controller

Service

DAO Repository

Entity

Test 

Maven

Swagger config file

docker compose for MongoDB

simple frontend UI using React

## Endpoints

**GET** /`url/{shortUrl}`

Redirects the user to the full URL associated with the given short URL.

Parameters: 

`shortUrl`: (required) The shortened URL.
Responses

`301 Move Permanently`: The user is successfully redirected to the full URL.

`404 Not Found`: The provided short URL is not valid or has expired.
Example Request: HTTP/1.1

Example Response: HTTP/1.1 200 OK Content-Type: application/json
```javascript      
{
    "id": 1,
    "url": "https://www.example.com",
    "createdDate": "2023/12/12 12:00:00",
    "shortUrl": "https://localhost:8080/url/000000"
}
```

**POST** `/url`

Creates a new shortened URL.

Parameters

`url` : (required) the original URL
Responses

`201 Created`: The URL is successfully created and the location of the new resource is returned in the Location header.

`400 Bad Request`: The provided url or short url is not valid.

`500 Internal Server Error`: Unexpected error happened in the server.

Example Request: HTTP/1.1 Content-Type: application/json

```javascript
{
    "url": "https://www.example.com",
    "expirationDate": null
}
```
Example Response: HTTP/1.1 201 Created Content-Type: application/json Location: /url/000000
```javascript
{
    "id": 2,
    "originalUrl": "https://www.example.com",
    "shortUrl": "https://localhost:8080/url/00000",
    "createdDate": "2022/01/01 12:00:00",
    "expirationDate": "2022/01/05 12:00:00",
}
```
You can also test endpoint on swagger! 
http://localhost:8080/swagger-ui/index.html
<img width="1496" alt="截圖 2023-08-27 下午3 19 01" src="https://github.com/AdrianaChuu/Url-Shortener-Service/assets/134931782/60a901f9-c295-4985-9e7a-323170779ca2">




## Functional Requirements:

- A user should provide a URL and receive a shoretened URL

- A user should be redirected to the original URL when using the shoretened URL

- URL valid for a configurable amount of time, if the URL has expired, the DB will delete the old shoretened URL and ask user to re-generate

- If the user enter the URL that already exist, will receive the same shoretened URL and extend the expiration Date

## Non-Functional Requirements:

- Scalability
- Performance (Max Latency e.g. 100 MS with 99.999) and Elasticity
- Availability

