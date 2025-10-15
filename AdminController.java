package com.sara.controller;

import com.sara.entity.Request;
import com.sara.entity.Resource;
import com.sara.entity.User;
import com.sara.entity.VolunteerAssignment;
import com.sara.service.RequestService;
import com.sara.service.ResourceService;
import com.sara.service.UserService;
import com.sara.service.VolunteerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    
    private final UserService userService;
    private final ResourceService resourceService;
    private final RequestService requestService;
    private final VolunteerService volunteerService;
    
    public AdminController(UserService userService, ResourceService resourceService, 
                          RequestService requestService, VolunteerService volunteerService) {
        this.userService = userService;
        this.resourceService = resourceService;
        this.requestService = requestService;
        this.volunteerService = volunteerService;
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        model.addAttribute("totalUsers", userService.getTotalUsersCount());
        model.addAttribute("totalDonors", userService.getUsersCountByRole(User.UserRole.DONOR));
        model.addAttribute("totalVolunteers", userService.getUsersCountByRole(User.UserRole.VOLUNTEER));
        model.addAttribute("totalVictims", userService.getUsersCountByRole(User.UserRole.VICTIM));
        
        model.addAttribute("totalResources", resourceService.getTotalResourcesCount());
        model.addAttribute("availableResources", resourceService.getAvailableResourcesCount());
        model.addAttribute("verifiedResources", resourceService.findVerifiedResources().size());
        
        model.addAttribute("totalRequests", requestService.getTotalRequestsCount());
        model.addAttribute("openRequests", requestService.getOpenRequestsCount());
        model.addAttribute("fulfilledRequests", requestService.getFulfilledRequestsCount());
        
        model.addAttribute("totalAssignments", volunteerService.getTotalAssignmentsCount());
        model.addAttribute("completedAssignments", volunteerService.getCompletedAssignmentsCount());
        
        // Recent activity
        model.addAttribute("recentResources", resourceService.findAllResources().stream().limit(5).toList());
        model.addAttribute("recentRequests", requestService.findAllRequests().stream().limit(5).toList());
        
        return "admin/dashboard";
    }
    
    @GetMapping("/users")
    public String users(Model model, @RequestParam(required = false) User.UserRole role) {
        var users = (role != null) ? userService.findByRole(role) : userService.findAllUsers();
        
        model.addAttribute("users", users);
        model.addAttribute("userRoles", User.UserRole.values());
        model.addAttribute("selectedRole", role);
        
        return "admin/users";
    }
    
    @PostMapping("/users/{id}/toggle-status")
    public String toggleUserStatus(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.toggleUserStatus(id);
        redirectAttributes.addFlashAttribute("success", "User status updated successfully!");
        return "redirect:/admin/users";
    }
    
    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("success", "User deleted successfully!");
        return "redirect:/admin/users";
    }
    
    @GetMapping("/resources")
    public String resources(Model model, 
                           @RequestParam(required = false) Resource.ResourceType type,
                           @RequestParam(required = false) Resource.ResourceStatus status) {
        var resources = resourceService.findAllResources();
        
        if (type != null) {
            resources = resources.stream().filter(r -> r.getType() == type).toList();
        }
        if (status != null) {
            resources = resources.stream().filter(r -> r.getStatus() == status).toList();
        }
        
        model.addAttribute("resources", resources);
        model.addAttribute("resourceTypes", Resource.ResourceType.values());
        model.addAttribute("resourceStatuses", Resource.ResourceStatus.values());
        model.addAttribute("selectedType", type);
        model.addAttribute("selectedStatus", status);
        
        return "admin/resources";
    }
    
    @PostMapping("/resources/{id}/verify")
    public String verifyResource(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        resourceService.verifyResource(id);
        redirectAttributes.addFlashAttribute("success", "Resource verified successfully!");
        return "redirect:/admin/resources";
    }
    
    @PostMapping("/resources/{id}/status")
    public String updateResourceStatus(@PathVariable Long id, 
                                      @RequestParam Resource.ResourceStatus status,
                                      RedirectAttributes redirectAttributes) {
        resourceService.updateResourceStatus(id, status);
        redirectAttributes.addFlashAttribute("success", "Resource status updated successfully!");
        return "redirect:/admin/resources";
    }
    
    @PostMapping("/resources/{id}/delete")
    public String deleteResource(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        resourceService.deleteResource(id);
        redirectAttributes.addFlashAttribute("success", "Resource deleted successfully!");
        return "redirect:/admin/resources";
    }
    
    @GetMapping("/requests")
    public String requests(Model model, 
                          @RequestParam(required = false) Request.RequestStatus status,
                          @RequestParam(required = false) Request.UrgencyLevel urgency) {
        var requests = requestService.findAllRequests();
        
        if (status != null) {
            requests = requests.stream().filter(r -> r.getStatus() == status).toList();
        }
        if (urgency != null) {
            requests = requests.stream().filter(r -> r.getUrgency() == urgency).toList();
        }
        
        model.addAttribute("requests", requests);
        model.addAttribute("requestStatuses", Request.RequestStatus.values());
        model.addAttribute("urgencyLevels", Request.UrgencyLevel.values());
        model.addAttribute("selectedStatus", status);
        model.addAttribute("selectedUrgency", urgency);
        
        return "admin/requests";
    }
    
    @PostMapping("/requests/{id}/status")
    public String updateRequestStatus(@PathVariable Long id, 
                                     @RequestParam Request.RequestStatus status,
                                     RedirectAttributes redirectAttributes) {
        requestService.updateRequestStatus(id, status);
        redirectAttributes.addFlashAttribute("success", "Request status updated successfully!");
        return "redirect:/admin/requests";
    }
    
    @PostMapping("/requests/{id}/delete")
    public String deleteRequest(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        requestService.deleteRequest(id);
        redirectAttributes.addFlashAttribute("success", "Request deleted successfully!");
        return "redirect:/admin/requests";
    }
    
    @GetMapping("/assignments")
    public String assignments(Model model, 
                             @RequestParam(required = false) VolunteerAssignment.AssignmentStatus status) {
        var assignments = (status != null) 
            ? volunteerService.findByStatus(status)
            : volunteerService.findByStatus(VolunteerAssignment.AssignmentStatus.ASSIGNED); // Default to assigned
        
        model.addAttribute("assignments", assignments);
        model.addAttribute("assignmentStatuses", VolunteerAssignment.AssignmentStatus.values());
        model.addAttribute("selectedStatus", status);
        
        return "admin/assignments";
    }
    
    @PostMapping("/assignments/{id}/status")
    public String updateAssignmentStatus(@PathVariable Long id, 
                                        @RequestParam VolunteerAssignment.AssignmentStatus status,
                                        RedirectAttributes redirectAttributes) {
        volunteerService.updateAssignmentStatus(id, status);
        redirectAttributes.addFlashAttribute("success", "Assignment status updated successfully!");
        return "redirect:/admin/assignments";
    }
    
    @PostMapping("/assignments/{id}/delete")
    public String deleteAssignment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        volunteerService.deleteAssignment(id);
        redirectAttributes.addFlashAttribute("success", "Assignment deleted successfully!");
        return "redirect:/admin/assignments";
    }
}