package com.revworkforce.service.impl;

import com.revworkforce.entity.Employee;
import com.revworkforce.entity.PerformanceReview;
import com.revworkforce.exception.ResourceNotFoundException;
import com.revworkforce.repository.EmployeeRepository;
import com.revworkforce.repository.PerformanceReviewRepository;
import com.revworkforce.service.PerformanceReviewService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PerformanceReviewServiceImpl implements PerformanceReviewService {

    private final PerformanceReviewRepository reviewRepository;
    private final EmployeeRepository employeeRepository;

    public PerformanceReviewServiceImpl(PerformanceReviewRepository reviewRepository,
                                        EmployeeRepository employeeRepository) {
        this.reviewRepository = reviewRepository;
        this.employeeRepository = employeeRepository;
    }

    // ✅ Manager creates review
    @Override
    public PerformanceReview createReview(Long employeeId,
                                          Long managerId,
                                          PerformanceReview review) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        Employee manager = employeeRepository.findById(managerId)
                .orElseThrow(() -> new ResourceNotFoundException("Manager not found"));

        review.setEmployee(employee);
        review.setManager(manager);
        review.setReviewDate(LocalDate.now());

        return reviewRepository.save(review);
    }

    // ✅ Manager updates review
    @Override
    public PerformanceReview updateReview(Long reviewId,
                                          PerformanceReview updatedReview) {

        PerformanceReview existing = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        existing.setRating(updatedReview.getRating());
        existing.setFeedback(updatedReview.getFeedback());
        existing.setReviewPeriod(updatedReview.getReviewPeriod());

        return reviewRepository.save(existing);
    }

    // ✅ Employee views own reviews
    @Override
    public List<PerformanceReview> getReviewsByEmployee(Long employeeId) {
        return reviewRepository.findByEmployeeId(employeeId);
    }

    // ✅ Manager views team reviews
    @Override
    public List<PerformanceReview> getReviewsByManager(Long managerId) {
        return reviewRepository.findByManagerId(managerId);
    }
}