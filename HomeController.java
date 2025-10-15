package com.sara.controller;

import com.sara.entity.Resource;
import com.sara.entity.Request;
import com.sara.service.ResourceService;
import com.sara.service.RequestService;
import com.sara.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {
    
    private final ResourceService resourceService;
    private final RequestService requestService;
    private final UserService userService;
    
    public HomeController(ResourceService resourceService, RequestService requestService, UserService userService) {
        this.resourceService = resourceService;
        this.requestService = requestService;
        this.userService = userService;
    }
    
    @GetMapping({"/", "/home"})
    public String home(Model model) {
        // Get recent resources and requests for the home page
        List<Resource> recentResources = resourceService.findAvailableResources().stream()
                .limit(6)
                .toList();
        List<Request> urgentRequests = requestService.findOpenRequests().stream()
                .limit(6)
                .toList();
        
        model.addAttribute("recentResources", recentResources);
        model.addAttribute("urgentRequests", urgentRequests);
        
        return "home";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        
        var user = userService.findByUsername(principal.getName());
        if (user.isEmpty()) {
            return "redirect:/login";
        }
        
        model.addAttribute("user", user.get());
        
        // Add role-specific dashboard data
        switch (user.get().getRole()) {
            case ADMIN -> {
                model.addAttribute("totalUsers", userService.getTotalUsersCount());
                model.addAttribute("totalResources", resourceService.getTotalResourcesCount());
                model.addAttribute("totalRequests", requestService.getTotalRequestsCount());
                model.addAttribute("openRequests", requestService.getOpenRequestsCount());
                return "admin/dashboard";
            }
            case DONOR -> {
                model.addAttribute("userResources", resourceService.findByUser(user.get()));
                return "donor/dashboard";
            }
            case VOLUNTEER -> {
                model.addAttribute("availableRequests", requestService.findOpenRequests().stream().limit(10).toList());
                return "volunteer/dashboard";
            }
            case VICTIM -> {
                model.addAttribute("userRequests", requestService.findByUser(user.get()));
                return "victim/dashboard";
            }
            default -> {
                return "dashboard";
            }
        }
    }
    
    @GetMapping("/resources")
    public String resources(Model model, 
                           @RequestParam(required = false) Resource.ResourceType type,
                           @RequestParam(required = false) String location) {
        List<Resource> resources;
        
        if (type != null || (location != null && !location.isEmpty())) {
            resources = resourceService.findFilteredResources(type, location);
        } else {
            resources = resourceService.findAvailableResources();
        }
        
        model.addAttribute("resources", resources);
        model.addAttribute("resourceTypes", Resource.ResourceType.values());
        model.addAttribute("selectedType", type);
        model.addAttribute("selectedLocation", location);
        
        return "resources";
    }
    
    @GetMapping("/requests")
    public String requests(Model model,
                          @RequestParam(required = false) Request.UrgencyLevel urgency,
                          @RequestParam(required = false) String location) {
        List<Request> requests;
        
        if (urgency != null || (location != null && !location.isEmpty())) {
            requests = requestService.findFilteredRequests(urgency, location);
        } else {
            requests = requestService.findOpenRequests();
        }
        
        model.addAttribute("requests", requests);
        model.addAttribute("urgencyLevels", Request.UrgencyLevel.values());
        model.addAttribute("selectedUrgency", urgency);
        model.addAttribute("selectedLocation", location);
        
        return "requests";
    }
    
    @GetMapping("/about")
    public String about() {
        return "about";
    }
    
    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }
}