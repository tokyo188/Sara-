package com.sara.repository;

import com.sara.entity.Request;
import com.sara.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByUser(User user);
    List<Request> findByStatus(Request.RequestStatus status);
    List<Request> findByUrgency(Request.UrgencyLevel urgency);
    
    @Query("SELECT r FROM Request r WHERE r.status = 'OPEN' ORDER BY r.urgency DESC, r.createdAt ASC")
    List<Request> findOpenRequestsByUrgency();
    
    @Query("SELECT r FROM Request r WHERE " +
           "(:urgency IS NULL OR r.urgency = :urgency) AND " +
           "(:location IS NULL OR LOWER(r.location) LIKE LOWER(CONCAT('%', :location, '%'))) AND " +
           "r.status = 'OPEN'")
    List<Request> findFilteredRequests(@Param("urgency") Request.UrgencyLevel urgency, 
                                     @Param("location") String location);
    
    List<Request> findByUserOrderByCreatedAtDesc(User user);
    
    @Query("SELECT COUNT(r) FROM Request r WHERE r.status = :status")
    long countByStatus(@Param("status") Request.RequestStatus status);
}