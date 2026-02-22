package com.revworkforce.service.impl;
import com.revworkforce.entity.LeaveBalance;
import java.util.List;
import com.revworkforce.entity.*;
import com.revworkforce.enums.LeaveStatus;
import com.revworkforce.repository.*;
import com.revworkforce.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import com.revworkforce.entity.Notification;
import com.revworkforce.repository.NotificationRepository;

@Service
@RequiredArgsConstructor
public class LeaveServiceImpl implements LeaveService {

    private final LeaveApplicationRepository leaveApplicationRepository;
    private final EmployeeRepository employeeRepository;
    private final LeaveTypeRepository leaveTypeRepository;
    private final LeaveBalanceRepository leaveBalanceRepository;
    private final NotificationRepository notificationRepository;

    // ================= APPLY LEAVE =================

    @Override
    public LeaveApplication applyLeave(Long employeeId,
                                       Long leaveTypeId,
                                       LocalDate startDate,
                                       LocalDate endDate,
                                       String reason) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        LeaveType leaveType = leaveTypeRepository.findById(leaveTypeId)
                .orElseThrow(() -> new RuntimeException("Leave type not found"));

        boolean overlap = leaveApplicationRepository
                .existsByEmployee_IdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        employeeId, endDate, startDate);

        if (overlap) {
            throw new RuntimeException("Leave request overlaps with existing leave");
        }

        long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        LeaveBalance leaveBalance = leaveBalanceRepository
                .findByEmployee_IdAndLeaveType_Id(employeeId, leaveTypeId)
                .orElseThrow(() -> new RuntimeException("Leave balance not found"));

        if (leaveBalance.getRemainingLeaves() < days) {
            throw new RuntimeException("Insufficient leave balance");
        }

        LeaveApplication leaveApplication = new LeaveApplication();
        leaveApplication.setEmployee(employee);
        leaveApplication.setLeaveType(leaveType);
        leaveApplication.setStartDate(startDate);
        leaveApplication.setEndDate(endDate);
        leaveApplication.setReason(reason);
        leaveApplication.setStatus(LeaveStatus.PENDING);

        LeaveApplication savedLeave = leaveApplicationRepository.save(leaveApplication);

// 🔔 Create notification for manager
        Employee manager = employee.getManager();

        if (manager != null) {
            Notification notification = new Notification();
            notification.setManager(manager);
            notification.setMessage(
                    employee.getFirstName() + " applied for leave from "
                            + savedLeave.getStartDate() + " to "
                            + savedLeave.getEndDate()
            );
            notification.setReadStatus(false);

            notificationRepository.save(notification);
        }

        return savedLeave;
    }

    // ================= APPROVE LEAVE =================

    @Override
    public LeaveApplication approveLeave(Long leaveApplicationId, String managerComment) {

        LeaveApplication leave = leaveApplicationRepository.findById(leaveApplicationId)
                .orElseThrow(() -> new RuntimeException("Leave not found"));

        if (!leave.getStatus().equals(LeaveStatus.PENDING)) {
            throw new RuntimeException("Only pending leaves can be approved");
        }

        leave.setStatus(LeaveStatus.APPROVED);
        leave.setManagerComments(managerComment);

        long days = ChronoUnit.DAYS.between(
                leave.getStartDate(),
                leave.getEndDate()) + 1;

        LeaveBalance balance = leaveBalanceRepository
                .findByEmployee_IdAndLeaveType_Id(
                        leave.getEmployee().getId(),
                        leave.getLeaveType().getId())
                .orElseThrow(() -> new RuntimeException("Leave balance not found"));

        balance.setUsedLeaves(balance.getUsedLeaves() + (int) days);
        balance.setRemainingLeaves(balance.getRemainingLeaves() - (int) days);

        leaveBalanceRepository.save(balance);

        return leaveApplicationRepository.save(leave);
    }

    // ================= REJECT LEAVE =================

    @Override
    public LeaveApplication rejectLeave(Long leaveApplicationId, String managerComment) {

        LeaveApplication leave = leaveApplicationRepository.findById(leaveApplicationId)
                .orElseThrow(() -> new RuntimeException("Leave not found"));

        if (!leave.getStatus().equals(LeaveStatus.PENDING)) {
            throw new RuntimeException("Only pending leaves can be rejected");
        }

        leave.setStatus(LeaveStatus.REJECTED);
        leave.setManagerComments(managerComment);

        return leaveApplicationRepository.save(leave);
    }

    // ================= CANCEL LEAVE =================

    @Override
    public LeaveApplication cancelLeave(Long leaveApplicationId) {

        LeaveApplication leave = leaveApplicationRepository.findById(leaveApplicationId)
                .orElseThrow(() -> new RuntimeException("Leave not found"));

        if (!leave.getStatus().equals(LeaveStatus.PENDING)) {
            throw new RuntimeException("Only pending leaves can be cancelled");
        }

        leave.setStatus(LeaveStatus.CANCELLED);

        return leaveApplicationRepository.save(leave);
    }

    // ================= VIEW EMPLOYEE LEAVES =================

    @Override
    public List<LeaveApplication> getEmployeeLeaves(Long employeeId) {
        return leaveApplicationRepository.findByEmployee_Id(employeeId);
    }

    // ================= VIEW MANAGER TEAM LEAVES =================

    @Override
    public List<LeaveApplication> getManagerTeamLeaves(Long managerId) {
        return leaveApplicationRepository.findByEmployee_Manager_Id(managerId);
    }
    @Override
    public List<LeaveApplication> getAllLeaves() {
        return leaveApplicationRepository.findAll();
    }

    @Override
    public List<LeaveApplication> getTeamLeaveCalendar(Long managerId) {
        return leaveApplicationRepository.findByEmployee_Manager_Id(managerId);
    }

    @Override
    public List<LeaveBalance> getEmployeeLeaveBalance(Long employeeId) {
        return leaveBalanceRepository.findByEmployeeId(employeeId);
    }
}