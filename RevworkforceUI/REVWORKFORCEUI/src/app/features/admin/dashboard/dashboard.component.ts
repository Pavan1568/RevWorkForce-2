import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {

  totalUsers = 0;
  totalEmployees = 0;
  totalDepartments = 0;
  pendingLeaves = 0;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadCounts();
  }

  loadCounts() {

    this.http.get<number>('http://localhost:8080/api/admin/users/count')
      .subscribe(data => this.totalUsers = data);

    this.http.get<number>('http://localhost:8080/api/employees/count')
      .subscribe(data => this.totalEmployees = data);

    this.http.get<number>('http://localhost:8080/api/admin/departments/count')
      .subscribe(data => this.totalDepartments = data);

    this.http.get<number>('http://localhost:8080/api/leaves/pending/count')
      .subscribe(data => this.pendingLeaves = data);
  }
}