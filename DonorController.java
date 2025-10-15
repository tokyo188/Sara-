package com.sara.controller;

import com.sara.entity.Resource;
import com.sara.entity.User;
import com.sara.service.ResourceService;
import com.sara.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/donor")
@PreAuthorize("hasRole('DONOR') or hasRole('ADMIN')")
public class DonorController {
    
    private final ResourceService resourceService;
    private final UserService userService;
    
    public DonorController(ResourceService resourceService, UserService userService) {
        this.resourceService = resourceService;
        this.userService = userService;
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        Optional<User> user = userService.findByUsername(principal.getName());
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            model.addAttribute("userResources", resourceService.findByUser(user.get()));
            model.addAttribute("totalResources", resourceService.findByUser(user.get()).size());
            model.addAttribute("availableResources", 
                resourceService.findByUser(user.get()).stream()
                    .filter(r -> r.getStatus() == Resource.ResourceStatus.AVAILABLE)
                    .count());
        }
        return "donor/dashboard";
    }
    
    @GetMapping("/resources")
    public String resources(Model model, Principal principal) {
        Optional<User> user = userService.findByUsername(principal.getName());
        if (user.isPresent()) {
            model.addAttribute("resources", resourceService.findByUser(user.get()));
        }
        return "donor/resources";
    }
    
    @GetMapping("/resources/new")
    public String newResourceForm(Model model) {
        model.addAttribute("resource", new Resource());
        // Only allow donor-appropriate types
        Resource.ResourceType[] donorTypes = {
            Resource.ResourceType.FOOD,
            Resource.ResourceType.WATER,
            Resource.ResourceType.SHELTER,
            Resource.ResourceType.MEDICINE,
            Resource.ResourceType.CLOTHING,
            Resource.ResourceType.BLANKETS,
            Resource.ResourceType.FIRST_AID,
            Resource.ResourceType.TOOLS,
            Resource.ResourceType.OTHER
        };
        model.addAttribute("resourceTypes", donorTypes);
        return "donor/resource-form";
    }
    
    @PostMapping("/resources")
    public String createResource(@Valid @ModelAttribute("resource") Resource resource,
                                BindingResult result,
                                Model model,
                                Principal principal,
                                RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            Resource.ResourceType[] donorTypes = {
                Resource.ResourceType.FOOD,
                Resource.ResourceType.WATER,
                Resource.ResourceType.SHELTER,
                Resource.ResourceType.MEDICINE,
                Resource.ResourceType.CLOTHING,
                Resource.ResourceType.BLANKETS,
                Resource.ResourceType.FIRST_AID,
                Resource.ResourceType.TOOLS,
                Resource.ResourceType.OTHER
            };
            model.addAttribute("resourceTypes", donorTypes);
            return "donor/resource-form";
        }
        
        Optional<User> user = userService.findByUsername(principal.getName());
        if (user.isPresent()) {
            resource.setUser(user.get());
            resourceService.saveResource(resource);
            redirectAttributes.addFlashAttribute("success", "Resource posted successfully!");
        }
        
        return "redirect:/donor/resources";
    }
    
    @GetMapping("/resources/{id}/edit")
    public String editResourceForm(@PathVariable String id, Model model, Principal principal) {
        // Defensive: If id is not a number, redirect to resources
        Long resourceId = null;
        try {
            resourceId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            return "redirect:/donor/resources";
        }
        Optional<Resource> resource = resourceService.findById(resourceId);
        Optional<User> user = userService.findByUsername(principal.getName());
        if (resource.isPresent() && user.isPresent()) {
            // Check if the resource belongs to the current user
            if (!resource.get().getUser().getId().equals(user.get().getId())) {
                return "redirect:/donor/resources";
            }
            model.addAttribute("resource", resource.get());
            model.addAttribute("resourceTypes", Resource.ResourceType.values());
            return "donor/resource-form";
        }
        return "redirect:/donor/resources";
    }
    
    @PostMapping("/resources/{id}/edit")
    public String updateResource(@PathVariable Long id,
                                @Valid @ModelAttribute("resource") Resource resource,
                                BindingResult result,
                                Model model,
                                Principal principal,
                                RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            model.addAttribute("resourceTypes", Resource.ResourceType.values());
            return "donor/resource-form";
        }
        
        Optional<Resource> existingResource = resourceService.findById(id);
        Optional<User> user = userService.findByUsername(principal.getName());
        
        if (existingResource.isPresent() && user.isPresent()) {
            // Check if the resource belongs to the current user
            if (!existingResource.get().getUser().getId().equals(user.get().getId())) {
                return "redirect:/donor/resources";
            }
            
            Resource updatedResource = existingResource.get();
            updatedResource.setName(resource.getName());
            updatedResource.setDescription(resource.getDescription());
            updatedResource.setType(resource.getType());
            updatedResource.setQuantity(resource.getQuantity());
            updatedResource.setLocation(resource.getLocation());
            updatedResource.setContactInfo(resource.getContactInfo());
            updatedResource.setStatus(resource.getStatus());
            
            resourceService.updateResource(updatedResource);
            redirectAttributes.addFlashAttribute("success", "Resource updated successfully!");
        }
        
        return "redirect:/donor/resources";
    }
    
    @PostMapping("/resources/{id}/delete")
    public String deleteResource(@PathVariable Long id, 
                                Principal principal,
                                RedirectAttributes redirectAttributes) {
        
        Optional<Resource> resource = resourceService.findById(id);
        Optional<User> user = userService.findByUsername(principal.getName());
        
        if (resource.isPresent() && user.isPresent()) {
            // Check if the resource belongs to the current user
            if (resource.get().getUser().getId().equals(user.get().getId())) {
                resourceService.deleteResource(id);
                redirectAttributes.addFlashAttribute("success", "Resource deleted successfully!");
            }
        }
        
        return "redirect:/donor/resources";
    }
    
    @GetMapping("/resources/{id}")
    public String viewResource(@PathVariable Long id, Model model, Principal principal) {
        Optional<Resource> resource = resourceService.findById(id);
        Optional<User> user = userService.findByUsername(principal.getName());
        
        if (resource.isPresent() && user.isPresent()) {
            // Check if the resource belongs to the current user
            if (!resource.get().getUser().getId().equals(user.get().getId())) {
                return "redirect:/donor/resources";
            }
            
            model.addAttribute("resource", resource.get());
            return "donor/resources_view";
        }
        
        return "redirect:/donor/resources";
    }
}