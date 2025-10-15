package com.sara.service;

import com.sara.entity.VolunteerAssignment;
import com.sara.entity.User;
import com.sara.entity.Request;
import com.sara.repository.VolunteerAssignmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VolunteerService {
    
    private final VolunteerAssignmentRepository volunteerAssignmentRepository;
    
    public VolunteerService(VolunteerAssignmentRepository volunteerAssignmentRepository) {
        this.volunteerAssignmentRepository = volunteerAssignmentRepository;
    }
    
    public VolunteerAssignment assignVolunteer(User volunteer, Request request) {
        if (volunteerAssignmentRepository.existsByVolunteerAndRequest(volunteer, request)) {
            throw new IllegalStateException("Volunteer is already assigned to this request");
        }
        
        VolunteerAssignment assignment = new VolunteerAssignment(volunteer, request);
        return volunteerAssignmentRepository.save(assignment);
    }
    
    public Optional<VolunteerAssignment> findById(Long id) {
        return volunteerAssignmentRepository.findById(id);
    }
    
    public List<VolunteerAssignment> findByVolunteer(User volunteer) {
        return volunteerAssignmentRepository.findByVolunteerOrderByAssignedAtDesc(volunteer);
    }
    
    public List<VolunteerAssignment> findByRequest(Request request) {
        return volunteerAssignmentRepository.findByRequest(request);
    }
    
    public List<VolunteerAssignment> findByStatus(VolunteerAssignment.AssignmentStatus status) {
        return volunteerAssignmentRepository.findByStatus(status);
    }
    
    public VolunteerAssignment updateAssignment(VolunteerAssignment assignment) {
        return volunteerAssignmentRepository.save(assignment);
    }
    
    public void updateAssignmentStatus(Long id, VolunteerAssignment.AssignmentStatus status) {
        Optional<VolunteerAssignment> assignmentOpt = volunteerAssignmentRepository.findById(id);
        if (assignmentOpt.isPresent()) {
            VolunteerAssignment assignment = assignmentOpt.get();
            assignment.setStatus(status);
            
            if (status == VolunteerAssignment.AssignmentStatus.COMPLETED) {
                assignment.setCompletedAt(LocalDateTime.now());
            }
            
            volunteerAssignmentRepository.save(assignment);
        }
    }
    
    public void deleteAssignment(Long id) {
        volunteerAssignmentRepository.deleteById(id);
    }
    
    public boolean isVolunteerAssigned(User volunteer, Request request) {
        return volunteerAssignmentRepository.existsByVolunteerAndRequest(volunteer, request);
    }
    
    public long getTotalAssignmentsCount() {
        return volunteerAssignmentRepository.count();
    }
    
    public long getCompletedAssignmentsCount() {
        return volunteerAssignmentRepository.findByStatus(VolunteerAssignment.AssignmentStatus.COMPLETED).size();
    }
}