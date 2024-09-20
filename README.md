# Email Application

Welcome to the **Email Application**, a modern email client built using **Java** with **Spring Boot** for the backend and **React.js** for the frontend. This application allows users to efficiently compose and send emails through a user-friendly interface.

## Technologies Used

### Backend
- **Java**: Main programming language.
- **Spring Boot**: Framework for building the REST API.
- **IntelliJ IDEA**: IDE for backend development.

### Frontend
- **React.js**: JavaScript library for building user interfaces.
- **Vite**: Build tool that provides a faster development experience.
- **VS Code**: IDE for frontend development.

## Features

- Send emails to single or multiple recipients.
- Send emails with HTML content and file attachments.
- View inbox messages.
- User-friendly interface for easy email composition.

## Backend

The backend is developed using Spring Boot and provides a RESTful API for handling email operations. Below is a brief overview of the key components:

### Email Service User Interface

![Screenshot (7)](https://github.com/user-attachments/assets/e43a285b-aecf-4695-a7fa-2d3fc3212aff)

![Screenshot (8)](https://github.com/user-attachments/assets/93806d99-2fee-4de5-9e2b-83a81af7a109)

![Screenshot (9)](https://github.com/user-attachments/assets/dbc59198-8a44-4402-a349-430ae2301bc9)

![Screenshot (10)](https://github.com/user-attachments/assets/9bfeb759-3625-413d-9e96-ea70144b51d9)

![Screenshot (11)](https://github.com/user-attachments/assets/d2be9abf-fc5b-4626-9c50-082b0feabf6d)

![Screenshot (12)](https://github.com/user-attachments/assets/1cb9f78a-0325-4c60-85b4-77a253442c1e)

## API Endpoints

- POST /api/v1/email/send: Send an email with HTML content.
- POST /api/v1/email/send-with-file: Send an email with a file attachment.

## Usage

- Open your browser and navigate to http://localhost:5173 (or your configured frontend URL).
- Fill in the required fields (To, Subject, Message).
- Click "Send Email" to send your email.
