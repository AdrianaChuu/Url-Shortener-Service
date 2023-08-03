# Url-Shortener-Service
A URL shortening service with a RESTful API. This service should allow users to submit long URLs and receive short URLs that redirect to the original long URLs

Java Spring boot URL Shortening Service containing the following components:
WebClient Test
Controller
Service
DAO Repository
Entity
Test 
Maven
Swagger config file
docker compose for MongoDB

URL Redirect API
This API allows for the redirecting of shortened URLs to their original, full URLs.

Endpoints:
GET /url/{shortUrl}

Redirects the user to the full URL associated with the given short URL.

Parameters

shortUrl: (required) The shortened URL.
Responses

302 Found: The user is successfully redirected to the full URL.

404 Not Found: The provided short URL is not valid or has expired.
Example Request: HTTP/1.1

Example Response: HTTP/1.1 200 OK Content-Type: application/json
{
    "id": 1,
    "url": "https://www.example.com",
    "createdDate": "2023/12/12 12:00:00",
    "shortUrl": "https://localhost:8080/url/000000"
}

