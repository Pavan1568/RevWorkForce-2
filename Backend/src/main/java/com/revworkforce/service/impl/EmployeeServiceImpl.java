package com.revworkforce.service.impl;
import com.revworkforce.dto.ProfileResponseDTO;
import com.revworkforce.dto.UpdateProfileDTO;
import com.revworkforce.entity.*;
import com.revworkforce.enums.RoleType;
import com.revworkforce.repository.*;
import com.revworkforce.service.EmployeeService;
import org.springframework.stereotype.Service;
import com.revworkforce.dto.ManagerResponseDTO;
import com.revworkforce.dto.CreateEmployeeRequest;
import com.revworkforce.exception.ResourceNotFoundException;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final DesignationRepository designationRepository;
    private final LeaveTypeRepository leaveTypeRepository;
    private final LeaveBalanceRepository leaveBalanceRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               UserRepository userRepository,
                               DepartmentRepository departmentRepository,
                               DesignationRepository designationRepository,
                               LeaveTypeRepository leaveTypeRepository,
                               LeaveBalanceRepository leaveBalanceRepository) {

        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.designationRepository = designationRepository;
        this.leaveBalanceRepository = leaveBalanceRepository;
        this.leaveTypeRepository = leaveTypeRepository;
    }

    // ================= GET PROFILE =================

    @Override
    public ProfileResponseDTO getProfile(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        ProfileResponseDTO dto = new ProfileResponseDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setEmail(employee.getUser().getEmail());
        dto.setPhone(employee.getPhone());
        dto.setAddress(employee.getAddress());
        dto.setEmergencyContact(employee.getEmergencyContact());

        if (employee.getManager() != null) {
            dto.setManagerName(employee.getManager().getName());
        }

        return dto;
    }

    // ================= UPDATE PROFILE =================

    @Override
    public ProfileResponseDTO updateProfile(Long employeeId, UpdateProfileDTO dto) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employee.setPhone(dto.getPhone());
        employee.setAddress(dto.getAddress());
        employee.setEmergencyContact(dto.getEmergencyContact());

        employeeRepository.save(employee);

        return getProfile(employeeId);
    }

    @Override
    public List<Employee> getTeamMembers(Long managerId) {
        return employeeRepository.findByManager_Id(managerId);
    }

    @Override
    public void assignManager(Long employeeId, Long managerId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        Employee manager = employeeRepository.findById(managerId)
                .orElseThrow(() -> new ResourceNotFoundException("Manager not found"));

        employee.setManager(manager);

        employeeRepository.save(employee);
    }

    // ================= SEARCH METHODS =================

    @Override
    public List<Employee> searchByName(String name) {
        return employeeRepository.findByFirstNameContainingIgnoreCase(name);
    }

    @Override
    public List<Employee> searchByEmail(String email) {
        return employeeRepository.findByUser_EmailContainingIgnoreCase(email);
    }

    @Override
    public List<Employee> searchByDepartment(String department) {
        return employeeRepository.findByDepartment_NameContainingIgnoreCase(department);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }



    @Override
    public ManagerResponseDTO getReportingManager(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        if (employee.getManager() == null) {
            throw new ResourceNotFoundException("Reporting manager not assigned");
        }

        Employee manager = employee.getManager();

        return new ManagerResponseDTO(
                manager.getId(),
                manager.getFirstName() + " " + manager.getLastName(),
                manager.getUser().getEmail(),
                manager.getDepartment().getName(),
                manager.getDesignation().getName()
        );

    }
    @Override
    public List<Employee> getAllManagers() {
        return employeeRepository.findByUser_Roles_Name(RoleType.ROLE_MANAGER);
    }

    @Override
    public Employee createEmployeeForUser(Long userId, CreateEmployeeRequest request) {

        if (employeeRepository.existsByUserId(userId)) {
            throw new RuntimeException("Employee already exists for this user");
        }


        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!user.isActive()) {
            throw new RuntimeException("Cannot create employee for inactive user");
        }

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        Designation designation = designationRepository.findById(request.getDesignationId())
                .orElseThrow(() -> new ResourceNotFoundException("Designation not found"));

        Employee employee = new Employee();
        employee.setUser(user);
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setPhone(request.getPhone());
        employee.setAddress(request.getAddress());
        employee.setEmergencyContact(request.getEmergencyContact());
        employee.setDepartment(department);
        employee.setDesignation(designation);
        Employee savedEmployee = employeeRepository.save(employee);

        List<LeaveType> leaveTypes = leaveTypeRepository.findAll();

        for (LeaveType leaveType : leaveTypes) {

            LeaveBalance leaveBalance = new LeaveBalance();

            leaveBalance.setEmployee(savedEmployee);
            leaveBalance.setLeaveType(leaveType);

            int total = leaveType.getTotalDays();

            leaveBalance.setTotalAllocated(total);
            leaveBalance.setUsedLeaves(0);
            leaveBalance.setRemainingLeaves(total);

            leaveBalanceRepository.save(leaveBalance);
        }



        return savedEmployee;
    }
}