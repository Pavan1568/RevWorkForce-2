import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone:true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']   // ✅ ADD THIS LINE HERE

})
export class LoginComponent {

  email = '';
  password = '';

  constructor(private authService: AuthService,
              private router: Router) {}

  login() {

    const loginData = {
      email: this.email,
      password: this.password
    };

    this.authService.login(loginData).subscribe({
      next: (res) => {

  this.authService.saveToken(res.token);

  if (res.employeeId) {
    localStorage.setItem('employeeId', res.employeeId.toString());
  }

  const payload = JSON.parse(atob(res.token.split('.')[1]));
  const role = payload.roles?.[0];

  if (role === 'ROLE_ADMIN') {
    this.router.navigate(['/admin']);
  }
  else if (role === 'ROLE_MANAGER') {
    this.router.navigate(['/manager']);
  }
  else {
    this.router.navigate(['/employee']);
  }
}
    });
   
}
}