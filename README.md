# Smart Expense Tracker

A full-stack **Expense Tracker application** built using **Spring Boot and React** that helps users manage and track daily expenses.

---

## 🚀 Live Demo

**Frontend (Netlify)**  
https://smarttrackerapplication.netlify.app

**Backend (Render)**  
https://smart-tracker-hrkq.onrender.com

---

## 📌 Features

- User Registration
- User Login Authentication
- Add Expense
- View Expense List
- Update Expense
- Delete Expense
- Secure API communication
- Responsive frontend interface

---

## 🛠️ Tech Stack

### Backend
- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- MySQL
- REST API

### Frontend
- React
- Axios
- HTML
- CSS
- JavaScript

### Deployment
- Frontend deployed on Netlify
- Backend deployed on Render
- Code hosted on GitHub

---

## ⚙️ Backend Setup

### Clone repository

```bash
git clone https://github.com/yourusername/expense-tracker.git
Navigate to backend folder
cd backend
Configure database in application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/expense_tracker
spring.datasource.username=root
spring.datasource.password=yourpassword
Run Spring Boot application
mvn spring-boot:run

Backend will run on:

http://localhost:8080
⚙️ Frontend Setup
Navigate to frontend folder
cd frontend
Install dependencies
npm install
Start frontend
npm run dev

Frontend runs on:

http://localhost:5173
🔗 API Endpoints
Authentication

Register

POST /auth/register

Login

POST /auth/login
Expenses

Get all expenses

GET /expenses

Add expense

POST /expenses

Update expense

PUT /expenses/{id}

Delete expense

DELETE /expenses/{id}
📂 Project Structure
expense-tracker
│
├── backend
│   ├── controller
│   ├── service
│   ├── repository
│   ├── model
│   └── security
│
├── frontend
│   ├── components
│   ├── pages
│   ├── services
│   └── assets
📈 Future Improvements

JWT authentication

Expense category filtering

Monthly expense analytics

Dashboard charts

👩‍💻 Author

Rutuja Chavan
Java Developer

LinkedIn: https://www.linkedin.com/in/rutuja-chavan-5108b91bb/



