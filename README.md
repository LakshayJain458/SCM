# 📒 **SCM – Smart Contact Manager**

## 🚀 Overview  
**SCM (Smart Contact Manager)** is a full-stack, cloud-enabled contact management web app that empowers users to securely store, manage, and access their contacts with ease. It leverages **Spring Boot**, **Thymeleaf**, **Tailwind CSS**, and modern integrations like **Cloudinary** and **Excel Export** to deliver a complete user experience.

> 🌐 **Stay Organized. Stay Connected. Securely.**

---

## 🛠️ Tech Stack

| Layer         | Technologies Used                                             |
|---------------|---------------------------------------------------------------|
| **Backend**   | Spring Boot, Spring Security, Spring MVC, OAuth 2.0           |
| **Frontend**  | Thymeleaf, HTML5, Tailwind CSS, JavaScript, Flowbite UI       |
| **Database**  | PostgreSQL                                                    |
| **Cloud**     | Cloudinary (Image Upload), JavaSimpleMail (Email Verification)   |
| **Tools**     | Maven, Spring DevTools, Lombok            |
| **Deployment**| Runs locally on `http://localhost:8080`                       |

---

## ✨ Key Features

✅ **🔐 Secure User Authentication**  
- OAuth 2.0 Login (Google Sign-In)  
- Email Verification via SMTP (Spring Mail)  
- Password Encryption (BCrypt)  

✅ **👥 Advanced Contact Management**  
- Add, Edit, View, and Delete Contacts  
- Upload & store profile images securely using **Cloudinary**  
- Add tags, descriptions, and contact notes  
- Real-time search functionality

✅ **📤 Export Contacts to Excel**  
- One-click export of entire contact list as `.xlsx` file  
- Implemented with **Apache POI**

✅ **📬 Email Verification & Alerts**  
- Email verification link sent during signup  
- Feedback emails on contact addition or deletion (optional)

✅ **📊 User Dashboard**  
- Paginated contact view  
- Success/error toast notifications with Flowbite  
- Fully responsive layout with Tailwind CSS

✅ **🔒 Data Security**  
- Spring Security session management  
- Email-based account confirmation  
- Secure cloud storage (no local file leaks)

---

## 🚀 Installation & Setup

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/LakshayJain458/SCM.git
cd scm-smart-contact-manager
```

### 2️⃣ Run the Application
```bash
mvn spring-boot:run
```

Open [http://localhost:8080](http://localhost:8080) in your browser.

---

## 🧩 Folder Structure

SCM/
├── src/
│   └── main/
│       ├── java/com/scm/
│       │   ├── Configurations/     # Spring Security, Cloudinary, Mail configs
│       │   ├── Controller/         # Handles web requests and routing
│       │   ├── Service/            # Business logic and services
│       │   ├── forms/              # Form binding models (e.g., signup, login)
│       │   ├── helpers/            # Utilities (Cloudinary, Excel export, etc.)
│       │   ├── model/              # Entity classes like User, Contact, Role
│       │   ├── repo/               # Spring Data JPA repositories
│       │   └── ScmApplication.java # Main Spring Boot application file
│       │
│       └── resources/
│           ├── templates/          # Thymeleaf HTML templates
│           ├── static/             # Tailwind CSS, Flowbite, custom JS & CSS
│           └── application.properties  # App configuration (DB, Cloudinary, Mail, etc.)


---

## 💡 Bonus Ideas for the Future
- 📱 Mobile version with React Native  
- 🔄 Contact Import via CSV  
- 📂 Folder-wise contact categorization  
- 📬 Daily backup of contacts over email  

---

## 👨‍💻 Author  
**Lakshay Jain**  
🔗 [GitHub](https://github.com/LakshayJain458) | 💼 [LinkedIn](https://www.linkedin.com/in/lakshay-jain001/)

---

> 🔐 **SCM – Smart Contact Manager**  
> _The smarter way to manage your contacts. Cloud-powered. User-focused.
