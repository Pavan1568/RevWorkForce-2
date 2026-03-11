import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DepartmentService } from '../../../core/services/department.service';

@Component({
  selector: 'app-departments',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './departments.component.html'
})
export class DepartmentsComponent implements OnInit {

  departments: any[] = [];

  newDepartment: any = {
    name: ''
  };

  isSubmitting = false;

  constructor(private departmentService: DepartmentService) {}

  ngOnInit(): void {
    this.loadDepartments();
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

  createDepartment() {
    if (!this.newDepartment.name) {
      alert('Department name is required');
      return;
    }

    this.isSubmitting = true;

    this.departmentService.create(this.newDepartment).subscribe({
      next: () => {
        alert('Department created successfully');
        this.newDepartment = { name: '' };
        this.loadDepartments();
        this.isSubmitting = false;
      },
      error: (err) => {
        console.error('Failed to create department', err);
        alert('Failed to create department');
        this.isSubmitting = false;
      }
    });
  }

  deleteDepartment(dept: any) {
    if (!confirm(`Delete department "${dept.name}"?`)) {
      return;
    }

    this.departmentService.delete(dept.id).subscribe({
      next: () => {
        alert('Department deleted successfully');
        this.loadDepartments();
      },
      error: (err) => {
        console.error('Failed to delete department', err);
        alert('Failed to delete department');
      }
    });
  }
}
