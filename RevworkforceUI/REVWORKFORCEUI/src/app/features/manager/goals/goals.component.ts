import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-manager-goals',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="container mt-3">
      <h2 class="mb-3">Goals Overview</h2>
      <p class="text-muted">Goals are set by employees. Use Team Members and Performance Reviews to track progress.</p>
    </div>
  `
})
export class GoalsComponent {}
