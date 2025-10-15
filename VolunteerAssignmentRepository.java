package com.sara.repository;

import com.sara.entity.VolunteerAssignment;
import com.sara.entity.User;
import com.sara.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VolunteerAssignmentRepository extends JpaRepository<VolunteerAssignment, Long> {
    List<VolunteerAssignment> findByVolunteer(User volunteer);
    List<VolunteerAssignment> findByRequest(Request request);
    List<VolunteerAssignment> findByStatus(VolunteerAssignment.AssignmentStatus status);
    List<VolunteerAssignment> findByVolunteerOrderByAssignedAtDesc(User volunteer);
    Optional<VolunteerAssignment> findByVolunteerAndRequest(User volunteer, Request request);
    boolean existsByVolunteerAndRequest(User volunteer, Request request);
}