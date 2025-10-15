<!-- SARA - Smart Aid and Relief Alliance Project Instructions -->

This is a Spring Boot + Thymeleaf web application for disaster resource management.

## Project Overview
- **Name**: SARA (Smart Aid and Relief Alliance)
- **Type**: Disaster Resource Finder Web Application
- **Tech Stack**: Java 17, Spring Boot, Thymeleaf, H2 Database, Bootstrap 5
- **Build Tool**: Maven
- **Features**: User roles (Donor/Volunteer/Victim/Admin), resource posting/requesting, volunteer coordination

## Development Guidelines
- Follow Spring Boot MVC architecture
- Use Thymeleaf fragments for reusable components
- Implement role-based security with Spring Security
- Use Bootstrap 5 for responsive design
- Include comprehensive CRUD operations
- Maintain clean separation of concerns (Controller/Service/Repository layers)

## Key Components
- Entities: User, Resource, Request, VolunteerAssignment
- Controllers: HomeController, AuthController, DonorController, VolunteerController, VictimController, AdminController
- Services: UserService, ResourceService, RequestService, VolunteerService
- Templates: Responsive Thymeleaf templates with Bootstrap styling