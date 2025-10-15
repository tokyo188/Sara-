package com.sara.service;

import com.sara.entity.Resource;
import com.sara.entity.User;
import com.sara.repository.ResourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ResourceService {
    
    private final ResourceRepository resourceRepository;
    
    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }
    
    public Resource saveResource(Resource resource) {
        return resourceRepository.save(resource);
    }
    
    public Optional<Resource> findById(Long id) {
        return resourceRepository.findById(id);
    }
    
    public List<Resource> findAllResources() {
        return resourceRepository.findAll();
    }
    
    public List<Resource> findByUser(User user) {
        return resourceRepository.findByUserOrderByCreatedAtDesc(user);
    }
    
    public List<Resource> findAvailableResources() {
        return resourceRepository.findAvailableVerifiedResources();
    }
    
    public List<Resource> findByType(Resource.ResourceType type) {
        return resourceRepository.findByType(type);
    }
    
    public List<Resource> findByStatus(Resource.ResourceStatus status) {
        return resourceRepository.findByStatus(status);
    }
    
    public List<Resource> findVerifiedResources() {
        return resourceRepository.findByVerifiedTrue();
    }
    
    public List<Resource> findFilteredResources(Resource.ResourceType type, String location) {
        return resourceRepository.findFilteredResources(type, location);
    }
    
    public Resource updateResource(Resource resource) {
        return resourceRepository.save(resource);
    }
    
    public void deleteResource(Long id) {
        resourceRepository.deleteById(id);
    }
    
    public void verifyResource(Long id) {
        Optional<Resource> resourceOpt = resourceRepository.findById(id);
        if (resourceOpt.isPresent()) {
            Resource resource = resourceOpt.get();
            resource.setVerified(true);
            resourceRepository.save(resource);
        }
    }
    
    public void updateResourceStatus(Long id, Resource.ResourceStatus status) {
        Optional<Resource> resourceOpt = resourceRepository.findById(id);
        if (resourceOpt.isPresent()) {
            Resource resource = resourceOpt.get();
            resource.setStatus(status);
            resourceRepository.save(resource);
        }
    }
    
    public long getTotalResourcesCount() {
        return resourceRepository.count();
    }
    
    public long getAvailableResourcesCount() {
        return resourceRepository.findByStatus(Resource.ResourceStatus.AVAILABLE).size();
    }
}