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

        // 🔥 ADD THIS LINE
        review.setReviewPeriod(String.valueOf(LocalDate.now().getYear()));

        return reviewRepository.save(review);
    }
    // ✅ Manager updates review
    @Override
    public PerformanceReview updateReview(Long id, PerformanceReview review) {

        PerformanceReview existing = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        existing.setRating(review.getRating());
        existing.setFeedback(review.getFeedback());

        // ❌ DO NOT SET reviewPeriod here
        // ❌ DO NOT SET employee
        // ❌ DO NOT SET manager

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