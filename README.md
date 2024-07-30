# Spring Security Application

This project demonstrates user authentication and authorization using Spring Security, JWT tokens, and role-based access control.

## Features

- **User Registration**: Register new users securely.
- **Login**: Authenticate users and issue JWT tokens.
- **Role-Based Access**: Restrict access based on user roles.
- **JWT Tokens**: Use JWT for secure authentication.

## API Endpoints

- **Register User**: `POST /api/auth/register`
- **Login**: `POST /api/auth/login`
- **User Info**: `GET /api/user/info` (requires `User` role)
- **Admin Dashboard**: `GET /api/admin/dashboard` (requires `Admin` role)

## Postman Collection

A Postman collection to test the API endpoints has been included in the `resources` folder.
