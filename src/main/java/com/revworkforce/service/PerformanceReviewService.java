package com.revworkforce.service;

import com.revworkforce.entity.PerformanceReview;
import java.util.List;

public interface PerformanceReviewService {

    PerformanceReview createReview(Long employeeId,
                                   Long managerId,
                                   PerformanceReview review);

    PerformanceReview updateReview(Long reviewId,
                                   PerformanceReview review);

    List<PerformanceReview> getReviewsByEmployee(Long employeeId);

    List<PerformanceReview> getReviewsByManager(Long managerId);
}