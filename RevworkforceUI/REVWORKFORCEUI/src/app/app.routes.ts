import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [

  { path: '', redirectTo: 'login', pathMatch: 'full' },

  {
    path: 'login',
    loadComponent: () =>
      import('./features/auth/login/login.component')
        .then(m => m.LoginComponent)
  },

  // =========================
  // ADMIN
  // =========================
  {
    path: 'admin',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./core/layout/layout.component')
        .then(m => m.LayoutComponent),
    children: [

      { path: '', loadComponent: () =>
          import('./features/admin/dashboard/dashboard.component')
            .then(m => m.AdminDashboardComponent)
      },

      { path: 'users', loadComponent: () =>
          import('./features/admin/users/admin-users/admin-users.component')
            .then(m => m.AdminUsersComponent)
      },

      { path: 'employees', loadComponent: () =>
          import('./features/admin/employees/employees.component')
            .then(m => m.EmployeesComponent)
      },

      { path: 'departments', loadComponent: () =>
          import('./features/admin/departments/departments.component')
            .then(m => m.DepartmentsComponent)
      },

      { path: 'leave-types', loadComponent: () =>
          import('./features/admin/leavetypes/leavetypes.component')
            .then(m => m.LeavetypesComponent)
      },

      { path: 'holidays', loadComponent: () =>
          import('./features/admin/holidays/holidays.component')
            .then(m => m.HolidaysComponent)
      },

      { path: 'assign-manager', loadComponent: () =>
          import('./features/admin/assign-manager/assign-manager.component')
            .then(m => m.AssignManagerComponent)
      }
    ]
  },

  // =========================
  // MANAGER
  // =========================
  {
    path: 'manager',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./core/layout/layout.component')
        .then(m => m.LayoutComponent),
    children: [

      { path: '', loadComponent: () =>
          import('./features/manager/dashboard/dashboard.component')
            .then(m => m.ManagerDashboardComponent)
      },

      { path: 'team', loadComponent: () =>
          import('./features/manager/team-members/team-members.component')
            .then(m => m.TeamMembersComponent)
      },

      { path: 'approve-leaves', loadComponent: () =>
          import('./features/manager/approve-leaves/approve-leaves.component')
            .then(m => m.ApproveLeavesComponent)
      },

      { path: 'team-leaves', loadComponent: () =>
          import('./features/manager/leaves/leaves.component')
            .then(m => m.LeavesComponent)
      },

      { path: 'leave-balance', loadComponent: () =>
          import('./features/manager/leave-balance/leave-balance.component')
            .then(m => m.LeaveBalanceComponent)
      },

      { path: 'performance', loadComponent: () =>
          import('./features/manager/performance/performance.component')
            .then(m => m.PerformanceComponent)
      },

      { path: 'goals', loadComponent: () =>
          import('./features/manager/goals/goals.component')
            .then(m => m.GoalsComponent)
      }
    ]
  },

  // =========================
  // EMPLOYEE
  // =========================
  {
    path: 'employee',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./core/layout/layout.component')
        .then(m => m.LayoutComponent),
    children: [

      { path: '', loadComponent: () =>
          import('./features/employee/dashboard/dashboard.component')
            .then(m => m.EmployeeDashboardComponent)
      },

      { path: 'profile', loadComponent: () =>
          import('./features/employee/profile/profile.component')
            .then(m => m.ProfileComponent)
      },

      { path: 'apply-leave', loadComponent: () =>
          import('./features/employee/leaves/leaves.component')
            .then(m => m.LeavesComponent)
      },

      { path: 'leaves', loadComponent: () =>
          import('./features/employee/leaves/leaves.component')
            .then(m => m.LeavesComponent)
      },

      { path: 'leave-balance', loadComponent: () =>
          import('./features/employee/leave-balance/leave-balance.component')
            .then(m => m.LeaveBalanceComponent)
      },

      { path: 'goals', loadComponent: () =>
          import('./features/employee/goals/goals.component')
            .then(m => m.GoalsComponent)
      },

      { path: 'performance', loadComponent: () =>
          import('./features/employee/performance/performance.component')
            .then(m => m.PerformanceComponent)
      }
    ]
  },

  { path: '**', redirectTo: 'login' }

];