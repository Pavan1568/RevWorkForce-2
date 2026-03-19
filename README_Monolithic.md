# 🏢 RevWorkforce – Human Resource Management System

**RevWorkforce** is a robust, full-stack monolithic HRM platform designed to streamline the entire employee lifecycle. From seamless leave administration to data-driven performance evaluations, the system provides a centralized hub for organizational management.

---

## 🎨 Application Interface

### 🔐 Secure Login
![Login](images/login.png)

### 📊 Role-Based Dashboards
| **Admin Dashboard** | **Manager Dashboard** | **Employee Dashboard** |
| :---: | :---: | :---: |
| ![Admin](images/admin_dashboard.png) | ![Manager](images/manager_dashboard.png) | ![Employee](images/employee_dashboard.png) |

---

## 🛠 Tech Stack

![Java](https://img.shields.io/badge/Java-17-black?labelColor=ED8B00&logo=java&logoColor=white) ![SpringBoot](https://img.shields.io/badge/SpringBoot-3.x-black?labelColor=6DB33F&logo=springboot&logoColor=white) ![SpringSecurity](https://img.shields.io/badge/SpringSecurity-Auth-black?labelColor=6DB33F&logo=springsecurity&logoColor=white) ![Hibernate](https://img.shields.io/badge/Hibernate-ORM-black?labelColor=59666C&logo=hibernate&logoColor=white) ![MySQL](https://img.shields.io/badge/MySQL-8.x-black?labelColor=4479A1&logo=mysql&logoColor=white)

![Angular](https://img.shields.io/badge/Angular-18-black?labelColor=DD0031&logo=angular&logoColor=white) ![TypeScript](https://img.shields.io/badge/TypeScript-5.x-black?labelColor=3178C6&logo=typescript&logoColor=white) ![RXJS](https://img.shields.io/badge/RXJS-Reactive-black?labelColor=D31266&logo=reactivex&logoColor=white) ![Bootstrap](https://img.shields.io/badge/Bootstrap-5.x-black?labelColor=7952B3&logo=bootstrap&logoColor=white)

![Maven](https://img.shields.io/badge/Maven-Build-black?labelColor=C71A36&logo=apache-maven&logoColor=white) ![Git](https://img.shields.io/badge/Git-Control-black?labelColor=F05032&logo=git&logoColor=white) ![JWT](https://img.shields.io/badge/JWT-Secure-black?labelColor=000000&logo=jsonwebtokens&logoColor=white)

---

## 👥 Role-Based Features

### 🛠 Admin (System Controller)
- **Employee Hub**: Full lifecycle management—onboard, update, and manage HR records.
- **Org Management**: Configure departments, job designations, and reporting structures.
- **Leave Analytics**: Manage quotas, adjust balances, and generate department-wide reports.
- **Communication**: Create and post company-wide announcements.
- **Governance**: Access system activity logs and audit trails.

### 👨‍💼 Manager (Team Lead)
- **Team Insights**: View direct reports, their profiles, and hierarchy structures.
- **Workflow Control**: Approve or reject leave requests with mandatory justifications.
- **Performance Coaching**: Review employee performance docs, provide feedback, and assign ratings (1-5 scale).
- **Goal Monitoring**: Keep track of team goals and real-time progress.

### 🧑‍💻 Employee (End User)
- **Personal Portal**: Manage profile details, emergency contacts, and personal info.
- **Leave Self-Service**: View balances, apply for leaves, and track status transitions.
- **Performance Growth**: Submit self-assessments, set yearly goals, and track accomplishments.
- **Stay Connected**: Access the company holiday calendar and receive real-time notifications.

---

## 🏗 System Architecture

The application follows a **monolithic full-stack architecture**, ensuring tight integration between the business logic and the presentation layer.

```mermaid
graph TD
    UI[Angular Frontend] -->|REST API| BE[Spring Boot Backend]
    BE -->|Spring Security + JWT| Auth[Authentication Layer]
    BE -->|JPA/Hibernate| DB[(MySQL Database)]
    BE -->|Service Layer| Logic[HR Business Logic]
    Logic -->|Notifications| Push[In-App Notification System]
```

---

## 🎯 Technical Highlights
- **RBAC (Role-Based Access Control)**: Granular security for Admin, Manager, and Employee levels.
- **Decoupled Frontend**: Angular 18 with reactive state management (RXJS) and responsive design.
- **Secure Authentication**: JWT with Bcrypt password encoding.
- **Automated Workflows**: Full leave lifecycle and performance review automation.

---

## 📝 Future Scope
- 📧 **SMTP Integration**: Email alerts for leave decisions and performance feedback.
- 📊 **Advanced Analytics**: Interactive charts and data visualizations for HR metrics.
- 💰 **Payroll System**: Integrated salary and benefits management.
- 🔗 **Microservices Migration**: Next-gen scalability redesign.

---
