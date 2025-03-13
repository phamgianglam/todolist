This project is a side project. This is a proof of apply springboot frame
word to a practical project.

# Task Management API

This Spring Boot project provides a RESTful API for managing tasks and task lists, designed to help users track broader targets over weeks, months, or years.

## Features
- Create, update, delete, and retrieve tasks and task lists.
- Nested task lists for hierarchical goal management.
- Automatically mark a task list as done when all contained tasks (and sub-task lists) are done.
- User management for task ownership and tracking.

---

## Entity Structure

### 1. User
Represents an individual who owns and manages task lists and tasks.

| Field       | Type    | Description                |
|------------|---------|----------------------------|
| id         | Long    | Unique identifier           |
| username   | String  | User’s unique username       |
| email      | String  | User’s email address         |
| password   | String  | Encrypted password           |

---

### 2. Task
Represents a specific actionable item within a task list.

| Field       | Type    | Description                          |
|------------|---------|--------------------------------------|
| id         | Long    | Unique identifier                     |
| title      | String  | Brief description of the task          |
| description| String  | Detailed information about the task    |
| status     | Enum    | Task status: PENDING, IN_PROGRESS, DONE|
| dueDate    | Date    | Deadline for the task                  |
| parentTaskListId | Long | ID of the parent task list           |
| Owner      | Long | ID of the user own that task              |

---

### 3. Task List
Represents a collection of tasks or nested task lists to track broader goals.

| Field       | Type    | Description                                      |
|------------|---------|--------------------------------------------------|
| id         | Long    | Unique identifier                                 |
| name       | String  | Name of the task list                             |
| description| String  | Brief description of the overall goal             |
| status     | Enum    | Task list status: PENDING, IN_PROGRESS, DONE       |
| tasks      | List<Task> | Collection of tasks within the list             |
| subTaskLists | List<TaskList> | Nested task lists within the current list |
| Owner      | Long | ID of the user own that task              |

---

## API Endpoints

### User Endpoints
- **POST /api/users** - Create a new user
- **GET /api/users/{id}** - Get user by ID
- **PUT /api/users/{id}** - Update user details
- **DELETE /api/users/{id}** - Delete user

### Task Endpoints
- **POST /api/tasks** - Create a new task
- **GET /api/tasks/{id}** - Get task by ID
- **PUT /api/tasks/{id}** - Update task details
- **DELETE /api/tasks/{id}** - Delete task

### Task List Endpoints
- **POST /api/tasklists** - Create a new task list
- **GET /api/tasklists/{id}** - Get task list by ID
- **PUT /api/tasklists/{id}** - Update task list details
- **DELETE /api/tasklists/{id}** - Delete task list

---

## Business Rules
1. A task list can only be marked as done when all associated tasks and nested task lists are marked as done.
2. Tasks can be marked as "PENDING", "IN_PROGRESS", or "DONE".
3. Task lists can be nested to form hierarchical structures, allowing broad targets to be broken into smaller, manageable goals.

---

## Technology Stack
- Java 23
- Spring Boot 3.x
- Spring Data JPA
- PostgreSQL
- Spring Security
- JWT Authentication
- Lombok
- Swagger/OpenAPI
- Docker

---

## Setup Instructions
1. Clone the repository:
   ```bash
   git clone https://github.com/username/task-management-api.git
   cd task-management-api
   ```
2. Configure the database connection in `application.yml`.
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
4. Access the API documentation at:
   ```
   http://localhost:8080/swagger-ui.html
   ```

Let me know if you need further customizations or additions!

