package com.sara.repository;

import com.sara.entity.Resource;
import com.sara.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    List<Resource> findByUser(User user);
    List<Resource> findByStatus(Resource.ResourceStatus status);
    List<Resource> findByType(Resource.ResourceType type);
    List<Resource> findByVerifiedTrue();
    
    @Query("SELECT r FROM Resource r WHERE r.status = 'AVAILABLE' AND r.verified = true")
    List<Resource> findAvailableVerifiedResources();
    
    @Query("SELECT r FROM Resource r WHERE " +
           "(:type IS NULL OR r.type = :type) AND " +
           "(:location IS NULL OR LOWER(r.location) LIKE LOWER(CONCAT('%', :location, '%'))) AND " +
           "r.status = 'AVAILABLE' AND r.verified = true")
    List<Resource> findFilteredResources(@Param("type") Resource.ResourceType type, 
                                       @Param("location") String location);
    
    List<Resource> findByUserOrderByCreatedAtDesc(User user);
}