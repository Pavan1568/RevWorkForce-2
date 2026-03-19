import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-manager-goals',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="alert alert-info mt-3">
  Goals are defined by employees.  
  Managers can monitor progress through Performance Reviews and Team Members modules.
    </div>
  `
})
export class GoalsComponent {}
