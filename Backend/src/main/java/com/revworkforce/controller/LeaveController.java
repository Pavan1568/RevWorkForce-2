package com.revworkforce.controller;
import com.revworkforce.dto.ApplyLeaveRequestDTO;
import com.revworkforce.dto.LeaveResponseDTO;
import com.revworkforce.entity.LeaveApplication;
import com.revworkforce.entity.LeaveBalance;
import com.revworkforce.enums.LeaveStatus;
import com.revworkforce.service.LeaveService;
import com.revworkforce.repository.LeaveApplicationRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.revworkforce.enums.LeaveStatus;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    private final LeaveService leaveService;
    private final LeaveApplicationRepository leaveApplicationRepository;

    public LeaveController(LeaveService leaveService, LeaveApplicationRepository leaveApplicationRepository) {
        this.leaveService = leaveService;
        this.leaveApplicationRepository = leaveApplicationRepository;
    }

    // ==============================
    // EMPLOYEE: Apply Leave
    // ==============================
    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN')")
    @PostMapping("/apply")
    public LeaveResponseDTO applyLeave(@Valid @RequestBody ApplyLeaveRequestDTO request) {

        LeaveApplication leaveApplication = leaveService.applyLeave(
                request.getEmployeeId(),
                request.getLeaveTypeId(),
                request.getStartDate(),
                request.getEndDate(),
                request.getReason()
        );

        return mapToResponseDTO(leaveApplication);
    }



    // ==============================
    // EMPLOYEE: Cancel Leave
    // ==============================
    @PreAuthorize("hasRole('EMPLOYEE')")
    @PutMapping("/{id}/cancel")
    public LeaveResponseDTO cancelLeave(@PathVariable Long id) {

        LeaveApplication leaveApplication = leaveService.cancelLeave(id);
        return mapToResponseDTO(leaveApplication);
    }

    // ==============================
    // MANAGER / ADMIN: Approve Leave
    // ==============================
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @PutMapping("/{id}/approve")
    public LeaveResponseDTO approveLeave(
            @PathVariable Long id,
            @RequestParam String comment) {

        LeaveApplication leaveApplication = leaveService.approveLeave(id, comment);
        return mapToResponseDTO(leaveApplication);
    }

    @GetMapping("/pending/count")
    @PreAuthorize("hasRole('ADMIN')")
    public long getPendingLeaveCount() {
        return leaveApplicationRepository.countByStatus(LeaveStatus.PENDING);
    }

    // ==============================
    // MANAGER / ADMIN: Reject Leave
    // ==============================
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @PutMapping("/{id}/reject")
    public LeaveResponseDTO rejectLeave(@PathVariable Long id) {

        LeaveApplication leaveApplication = leaveService.rejectLeave(id, "Rejected");

        return mapToResponseDTO(leaveApplication);
    }

    // ==============================
    // EMPLOYEE: View Own Leaves
    // ==============================
    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN')")
    @GetMapping("/employee/{employeeId}")
    public List<LeaveResponseDTO> getEmployeeLeaves(@PathVariable Long employeeId) {

        return leaveService.getEmployeeLeaves(employeeId)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // View Leave Balance
    @GetMapping("/employee/{employeeId}/balance")
    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN')")
    public List<LeaveBalance> getMyLeaveBalance(@PathVariable Long employeeId) {
        return leaveService.getEmployeeLeaveBalance(employeeId);
    }

    // ==============================
    // MANAGER: View Team Leaves
    // ==============================
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @GetMapping("/manager/{managerId}")
    public List<LeaveResponseDTO> getTeamLeaves(@PathVariable Long managerId) {

        return leaveService.getManagerTeamLeaves(managerId)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // ==============================
    // ADMIN: View All Leaves
    // ==============================
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<LeaveResponseDTO> getAllLeaves() {

        return leaveService.getAllLeaves()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }


    @GetMapping("/manager/{managerId}/calendar")
    @PreAuthorize("hasRole('MANAGER')")
    public List<LeaveApplication> getTeamLeaveCalendar(@PathVariable Long managerId) {
        return leaveService.getTeamLeaveCalendar(managerId);
    }

    @GetMapping("/manager/employee/{employeeId}/balance")
    @PreAuthorize("hasRole('MANAGER')")
    public List<LeaveBalance> getEmployeeLeaveBalance(@PathVariable Long employeeId) {
        return leaveService.getEmployeeLeaveBalance(employeeId);
    }

    // ==============================
    // Private DTO Mapper
    // ==============================
    private LeaveResponseDTO mapToResponseDTO(LeaveApplication leaveApplication) {

        LeaveResponseDTO response = new LeaveResponseDTO();

        response.setId(leaveApplication.getId());
        response.setEmployeeId(leaveApplication.getEmployee().getId());
        response.setLeaveTypeId(leaveApplication.getLeaveType().getId());
        response.setStartDate(leaveApplication.getStartDate());
        response.setEndDate(leaveApplication.getEndDate());
        response.setReason(leaveApplication.getReason());
        response.setStatus(leaveApplication.getStatus().name());
        response.setManagerComments(leaveApplication.getManagerComments());

        return response;
    }


}