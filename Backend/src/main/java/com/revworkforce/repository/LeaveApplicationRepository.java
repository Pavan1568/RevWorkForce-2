package com.revworkforce.repository;
import com.revworkforce.entity.LeaveApplication;
import com.revworkforce.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface LeaveApplicationRepository
        extends JpaRepository<LeaveApplication, Long> {

    long countByStatus(LeaveStatus status);

    // Check overlapping leave
    boolean existsByEmployee_IdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Long employeeId,
            LocalDate endDate,
            LocalDate startDate);

    // Get leaves of an employee
    List<LeaveApplication> findByEmployee_Id(Long employeeId);

    // Get team leaves for manager
    List<LeaveApplication> findByEmployee_Manager_Id(Long managerId);

    void deleteByLeaveType_Id(Long leaveTypeId);

}