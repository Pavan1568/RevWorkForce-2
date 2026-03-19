import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EmployeeService } from '../../../core/services/employee.service';
import { UserService } from '../../../core/services/user.service';
import { DepartmentService } from '../../../core/services/department.service';
import { DesignationService } from '../../../core/services/designation.service';

@Component({
  selector: 'app-employees',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './employees.component.html'
})
export class EmployeesComponent implements OnInit {

  employees: any[] = [];
  users: any[] = [];
  departments: any[] = [];
  designations: any[] = [];

  selectedUserId: number | null = null;

  // Matches backend CreateEmployeeRequest DTO
  newEmployee: any = {
    firstName: '',
    lastName: '',
    phone: '',
    address: '',
    emergencyContact: '',
    departmentId: null,
    designationId: null
  };

  isSubmitting = false;

  constructor(
    private employeeService: EmployeeService,
    private userService: UserService,
    private departmentService: DepartmentService,
     private designationService: DesignationService
  ) {}

  ngOnInit(): void {
    this.loadEmployees();
    this.loadUsers();
    this.loadDepartments();
    this.loadDesignations();
  }

  loadEmployees() {
    this.employeeService.getAll().subscribe({
      next: (data) => {
        this.employees = data;
      },
      error: (err) => {
        console.error('Failed to load employees', err);
        alert('Failed to load employees');
      }
    });
  }

  loadUsers() {
    this.userService.getAllUsers().subscribe({
      next: (data) => {
        this.users = data;
      },
      error: (err) => {
        console.error('Failed to load users', err);
        alert('Failed to load users');
      }
    });
  }

  loadDepartments() {
    this.departmentService.getAll().subscribe({
      next: (data) => {
        this.departments = data;
      },
      error: (err) => {
        console.error('Failed to load departments', err);
        alert('Failed to load departments');
      }
    });
  }

  loadDesignations() {
  this.designationService.getAll().subscribe({
    next: (data) => {
      this.designations = data;
    },
    error: (err) => {
      console.error('Failed to load designations', err);
      alert('Failed to load designations');
    }
  });
}

  createEmployeeForUser() {
    if (!this.selectedUserId) {
      alert('Please select a User');
      return;
    }
    if (!this.newEmployee.firstName || !this.newEmployee.lastName) {
      alert('First name and Last name are required');
      return;
    }
    if (!this.newEmployee.departmentId || !this.newEmployee.designationId) {
      alert('Department and Designation are required');
      return;
    }

    this.isSubmitting = true;

    const payload = {
      firstName: this.newEmployee.firstName,
      lastName: this.newEmployee.lastName,
      phone: this.newEmployee.phone || null,
      address: this.newEmployee.address || null,
      emergencyContact: this.newEmployee.emergencyContact || null,
      departmentId: this.newEmployee.departmentId,
      designationId: this.newEmployee.designationId
    };

    this.employeeService.createForUser(this.selectedUserId, payload).subscribe({
      next: () => {
        alert('Employee created successfully');
        this.selectedUserId = null;
        this.newEmployee = {
          firstName: '',
          lastName: '',
          phone: '',
          address: '',
          emergencyContact: '',
          departmentId: null,
          designationId: null
        };
        this.loadEmployees();
        this.isSubmitting = false;
      },
      error: (err) => {
        console.error('Failed to create employee', err);
        alert('Failed to create employee');
        this.isSubmitting = false;
      }
    });
  }
}
