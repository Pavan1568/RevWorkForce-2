import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const authGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  const token = authService.getToken();
    console.log('Guard Token:', token);  // 👈 ADD THIS


  if (token) {
    return true;
  } else {
    router.navigate(['/login']);
    return false;
  }
};