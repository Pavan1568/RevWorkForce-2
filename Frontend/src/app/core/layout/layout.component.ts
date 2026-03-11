import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [CommonModule, RouterModule],
  styleUrls: ['./layout.component.css'],
  templateUrl: './layout.component.html'
})
export class LayoutComponent implements OnInit {

  role: string | null = '';
  openMenu: string | null = null;

  menuItems: any[] = [];

  constructor(private authService: AuthService,
              private router: Router) {}

  ngOnInit() {
    const token = this.authService.getToken();
    if (token) {
      const payload = JSON.parse(atob(token.split('.')[1]));
      this.role = payload.roles?.[0];
    }

    this.loadMenu();
  }

  loadMenu() {
    if (this.role === 'ROLE_ADMIN') {
      this.menuItems = [
        {
          label: 'Admin',
          icon: 'bi bi-speedometer2',
          children: [
            { label: 'Dashboard', route: '/admin' },
            { label: 'Users', route: '/admin/users' },
            { label: 'Employees', route: '/admin/employees' },
            { label: 'Departments', route: '/admin/departments' },
            { label: 'Leave Types', route: '/admin/leave-types' },
            { label: 'Holidays', route: '/admin/holidays' },
            { label: 'Assign Manager', route: '/admin/assign-manager' }
          ]
        }
      ];
    }

    if (this.role === 'ROLE_MANAGER') {
      this.menuItems = [
        {
          label: 'Manager',
          children: [
            { label: 'Dashboard', route: '/manager' },
            { label: 'Team Members', route: '/manager/team' },
            { label: 'Approve Leaves', route: '/manager/approve-leaves' },
            { label: 'Team Leave Calendar', route: '/manager/team-leaves' },
            { label: 'Leave Balance', route: '/manager/leave-balance' },
            { label: 'Performance Reviews', route: '/manager/performance' },
            { label: 'Goals Overview', route: '/manager/goals' }
          ]
        }
      ];
    }

    if (this.role === 'ROLE_EMPLOYEE') {
      this.menuItems = [
        {
          label: 'Employee',
          children: [
            { label: 'Dashboard', route: '/employee' },
            { label: 'My Profile', route: '/employee/profile' },
            { label: 'Apply Leave', route: '/employee/apply-leave' },
            { label: 'My Leaves', route: '/employee/leaves' },
            { label: 'Leave Balance', route: '/employee/leave-balance' },
            { label: 'My Goals', route: '/employee/goals' },
            { label: 'Performance Reviews', route: '/employee/performance' }
          ]
        }
      ];
    }
  }

  toggle(menu: string) {
    this.openMenu = this.openMenu === menu ? null : menu;
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}