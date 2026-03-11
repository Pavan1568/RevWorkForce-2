import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../../../core/services/user.service';

@Component({
  selector: 'app-admin-users',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-users.component.html',
  styleUrls: ['./admin-users.component.css']
})
export class AdminUsersComponent implements OnInit {

  users: any[] = [];

  newUser: any = {
    email: '',
    password: '',
    role: ''
  };

  isSubmitting = false;

  constructor(private userService: UserService) {}

  ngOnInit() {
    this.loadUsers();
  }

  // ================= LOAD USERS =================
  loadUsers() {
    this.userService.getAllUsers().subscribe({
      next: (data) => {
        this.users = data;
      },
      error: () => {
        alert('Failed to load users');
      }
    });
  }

  // ================= CREATE USER =================
  createUser() {
    if (!this.newUser.email || !this.newUser.password) {
      alert('Email and Password are required');
      return;
    }

    this.isSubmitting = true;

    const requestPayload = {
      user: {
        email: this.newUser.email,
        password: this.newUser.password
      },
      roleName: this.newUser.role || 'EMPLOYEE'
    };

    this.userService.createUser(requestPayload).subscribe({
      next: () => {
        alert('User created successfully');
        this.newUser = {
          email: '',
          password: '',
          role: ''
        };
        this.loadUsers();
        this.isSubmitting = false;
      },
      error: (err: any) => {
        console.error('Failed to create user', err);
        alert('Failed to create user');
        this.isSubmitting = false;
      }
    });
  }

  // ================= ACTIVATE / DEACTIVATE =================
  toggleStatus(user: any) {
    const newStatus = !user.active;

    this.userService.updateUserStatus(user.id, newStatus).subscribe({
      next: () => {
        user.active = newStatus; // update UI instantly
      },
      error: (err: any) => {
        console.error('Failed to update status', err);
        alert('Failed to update status');
      }
    });
  }
}