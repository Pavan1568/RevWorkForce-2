package com.revworkforce.dto;

public class EmployeeResponseDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String departmentName;
    private String managerName;

    public EmployeeResponseDTO(
            Long id,
            String firstName,
            String lastName,
            String email,
            String departmentName,
            String managerName
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.departmentName = departmentName;
        this.managerName = managerName;
    }

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getDepartmentName() { return departmentName; }
    public String getManagerName() { return managerName; }
}