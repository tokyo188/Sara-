package com.sara.service;

import com.sara.entity.Request;
import com.sara.entity.User;
import com.sara.repository.RequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RequestService {
    
    private final RequestRepository requestRepository;
    
    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }
    
    public Request saveRequest(Request request) {
        return requestRepository.save(request);
    }
    
    public Optional<Request> findById(Long id) {
        return requestRepository.findById(id);
    }
    
    public List<Request> findAllRequests() {
        return requestRepository.findAll();
    }
    
    public List<Request> findByUser(User user) {
        return requestRepository.findByUserOrderByCreatedAtDesc(user);
    }
    
    public List<Request> findOpenRequests() {
        return requestRepository.findOpenRequestsByUrgency();
    }
    
    public List<Request> findByStatus(Request.RequestStatus status) {
        return requestRepository.findByStatus(status);
    }
    
    public List<Request> findByUrgency(Request.UrgencyLevel urgency) {
        return requestRepository.findByUrgency(urgency);
    }
    
    public List<Request> findFilteredRequests(Request.UrgencyLevel urgency, String location) {
        return requestRepository.findFilteredRequests(urgency, location);
    }
    
    public Request updateRequest(Request request) {
        return requestRepository.save(request);
    }
    
    public void deleteRequest(Long id) {
        requestRepository.deleteById(id);
    }
    
    public void updateRequestStatus(Long id, Request.RequestStatus status) {
        Optional<Request> requestOpt = requestRepository.findById(id);
        if (requestOpt.isPresent()) {
            Request request = requestOpt.get();
            request.setStatus(status);
            requestRepository.save(request);
        }
    }
    
    public long getTotalRequestsCount() {
        return requestRepository.count();
    }
    
    public long getOpenRequestsCount() {
        return requestRepository.countByStatus(Request.RequestStatus.OPEN);
    }
    
    public long getFulfilledRequestsCount() {
        return requestRepository.countByStatus(Request.RequestStatus.FULFILLED);
    }
}