package com.revworkforce.controller;

import com.revworkforce.entity.PerformanceReview;
import com.revworkforce.service.PerformanceReviewService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/performance")
public class PerformanceReviewController {

    private final PerformanceReviewService reviewService;

    public PerformanceReviewController(PerformanceReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // ================= MANAGER =================

    // ✅ Manager creates review
    @PostMapping("/review")
    @PreAuthorize("hasRole('MANAGER')")
    public PerformanceReview createReview(@RequestParam Long employeeId,
                                          @RequestParam Long managerId,
                                          @RequestBody PerformanceReview review) {

        return reviewService.createReview(employeeId, managerId, review);
    }

    // ✅ Manager updates review
    @PutMapping("/{reviewId}")
    @PreAuthorize("hasRole('MANAGER')")
    public PerformanceReview updateReview(@PathVariable Long reviewId,
                                          @RequestBody PerformanceReview review) {

        return reviewService.updateReview(reviewId, review);
    }

    // ✅ Manager views team reviews
    @GetMapping("/team")
    @PreAuthorize("hasRole('MANAGER')")
    public List<PerformanceReview> getTeamReviews(@RequestParam Long managerId) {

        return reviewService.getReviewsByManager(managerId);
    }

    // ================= EMPLOYEE =================

    // ✅ Employee views own reviews
    @GetMapping("/my-reviews")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public List<PerformanceReview> getMyReviews(@RequestParam Long employeeId) {

        return reviewService.getReviewsByEmployee(employeeId);
    }
}