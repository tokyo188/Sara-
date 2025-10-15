# SARA - Smart Aid Resource Assistant

<p align="center">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.3.6-green.svg" alt="Spring Boot">
  <img src="https://img.shields.io/badge/Java-17+-blue.svg" alt="Java">
  <img src="https://img.shields.io/badge/Thymeleaf-Template%20Engine-orange.svg" alt="Thymeleaf">
  <img src="https://img.shields.io/badge/Bootstrap-5.3.0-purple.svg" alt="Bootstrap">
  <img src="https://img.shields.io/badge/Database-H2-red.svg" alt="H2 Database">
</p>

A comprehensive disaster resource management web application that connects victims, donors, and volunteers during natural disasters. SARA helps communities coordinate aid distribution, manage resource requests, and organize volunteer efforts.

## 🚀 Features

### 🏠 **Home & Public Pages**
- Modern responsive design with Bootstrap 5
- Public resource directory with search and filtering
- Real-time display of urgent requests
- About and Contact pages

### 👥 **Multi-Role User Management**
- **Victims**: Request help and browse available resources
- **Donors**: Post and manage resource donations
- **Volunteers**: View requests and coordinate assistance
- **Admins**: Comprehensive system management

### 📦 **Resource Management**
- Post available resources (food, water, shelter, medicine, etc.)
- Resource verification system for admins
- Status tracking (Available, Reserved, Delivered, Expired)
- Location-based filtering
- Contact information management

### 🆘 **Request System**
- Submit help requests with urgency levels
- Detailed descriptions and location information
- Status tracking (Open, In Progress, Fulfilled, Cancelled)
- Priority-based sorting (Critical, High, Medium, Low)

### 🤝 **Volunteer Coordination**
- Volunteer assignment to requests
- Assignment status tracking
- Volunteer dashboard with active assignments
- Assignment completion workflow

### 🛠 **Admin Dashboard**
- User management with role assignment
- Resource verification and status management
- Request oversight and status updates
- Volunteer assignment management
- System statistics and reporting

### 🔐 **Security & Authentication**
- Spring Security integration
- Role-based access control
- Secure user registration and login
- Session management

## 🛠 Tech Stack

- **Backend**: Java 17, Spring Boot 3.3.6
- **Frontend**: Thymeleaf, HTML5, CSS3, Bootstrap 5
- **Database**: H2 (in-memory for development)
- **Security**: Spring Security
- **Build Tool**: Maven
- **Validation**: Bean Validation (JSR-303)

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Git

### Installation & Running

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd sara-disaster-assistant
   ```

2. **Build the project**
   ```bash
   mvn clean compile
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - Main application: http://localhost:8080
   - H2 Database console: http://localhost:8080/h2-console
     - JDBC URL: `jdbc:h2:mem:saradb`
     - Username: `sa`
     - Password: (leave empty)

## 👤 Demo Accounts

The application comes pre-loaded with demo accounts for testing:

| Role | Username | Password | Description |
|------|----------|----------|-------------|
| Admin | admin | password | Full system access |
| Donor | donor1 | password | Can post resources |
| Volunteer | volunteer1 | password | Can help with requests |
| Victim | victim1 | password | Can request help |

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/sara/
│   │   ├── config/          # Security configuration
│   │   ├── controller/      # MVC controllers
│   │   ├── entity/          # JPA entities
│   │   ├── repository/      # Data repositories
│   │   ├── service/         # Business logic
│   │   └── SaraApplication.java
│   └── resources/
│       ├── static/          # CSS, JS, images
│       ├── templates/       # Thymeleaf templates
│       ├── application.properties
│       └── data.sql         # Sample data
```

## 🎨 Key Components

### Entities
- **User**: Multi-role user management (Admin, Donor, Volunteer, Victim)
- **Resource**: Available aid resources with verification
- **Request**: Help requests with urgency levels
- **VolunteerAssignment**: Volunteer-to-request assignments

### Controllers
- **HomeController**: Public pages and dashboard routing
- **AuthController**: Registration and authentication
- **DonorController**: Resource management for donors
- **VolunteerController**: Volunteer coordination
- **VictimController**: Request management for victims
- **AdminController**: Administrative functions

### Key Features
- **Spring Security**: Role-based access control
- **Thymeleaf Fragments**: Reusable UI components
- **Bootstrap Components**: Responsive design elements
- **CRUD Operations**: Complete resource lifecycle management
- **Form Validation**: Client and server-side validation

## 🔧 Configuration

### Database
- Development: H2 in-memory database
- Tables auto-created with sample data
- Accessible via H2 console for debugging

### Security
- Password encoding with BCrypt
- CSRF protection enabled
- Role-based URL protection
- Session-based authentication

### Thymeleaf
- Fragment-based templates
- Spring Security integration
- Bootstrap 5 styling
- Responsive design

## 📱 User Workflows

### For Victims
1. Register with VICTIM role
2. Login and access victim dashboard
3. Browse available resources
4. Submit new help requests
5. Track request status

### For Donors
1. Register with DONOR role
2. Login and access donor dashboard
3. Post available resources
4. Manage resource inventory
5. Update resource status

### For Volunteers
1. Register with VOLUNTEER role
2. Login and access volunteer dashboard
3. Browse open requests
4. Volunteer for requests
5. Update assignment status

### For Admins
1. Login with admin credentials
2. Access comprehensive admin dashboard
3. Manage users, resources, and requests
4. Verify resources and oversee operations
5. View system statistics

## 🚀 Development

### Running in Development Mode
```bash
mvn spring-boot:run
```

### Building for Production
```bash
mvn clean package
java -jar target/sara-disaster-assistant-1.0.0.jar
```

### Database Access
Access H2 console at http://localhost:8080/h2-console for development database inspection.

## 🎯 Future Enhancements

- **Location Services**: GPS integration for proximity-based matching
- **Real-time Notifications**: WebSocket-based updates
- **Mobile App**: React Native or Flutter companion app
- **Analytics Dashboard**: Advanced reporting and insights
- **Multi-language Support**: Internationalization
- **External APIs**: Weather, maps, and communication services
- **Production Database**: PostgreSQL or MySQL integration

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📞 Support

For support and questions:
- Create an issue in the GitHub repository
- Contact the development team
- Check the application's built-in contact form

---

<p align="center">
  <strong>SARA - Connecting communities in times of need</strong><br>
  Built with ❤️ using Spring Boot + Thymeleaf
</p>