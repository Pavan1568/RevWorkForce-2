import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HolidayService } from '../../../core/services/holiday.service';

@Component({
  selector: 'app-holidays',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './holidays.component.html'
})
export class HolidaysComponent implements OnInit {

  holidays: any[] = [];

  newHoliday: any = {
    name: '',
    date: ''
  };

  isSubmitting = false;

  constructor(private holidayService: HolidayService) {}

  ngOnInit(): void {
    this.loadHolidays();
  }

  loadHolidays() {
    this.holidayService.getAll().subscribe({
      next: (data) => {
        this.holidays = data;
      },
      error: (err) => {
        console.error('Failed to load holidays', err);
        alert('Failed to load holidays');
      }
    });
  }

  createHoliday() {
    if (!this.newHoliday.name || !this.newHoliday.date) {
      alert('Holiday name and date are required');
      return;
    }

    this.isSubmitting = true;

    this.holidayService.create(this.newHoliday).subscribe({
      next: () => {
        alert('Holiday created successfully');
        this.newHoliday = { name: '', date: '' };
        this.loadHolidays();
        this.isSubmitting = false;
      },
      error: (err) => {
        console.error('Failed to create holiday', err);
        alert('Failed to create holiday');
        this.isSubmitting = false;
      }
    });
  }
}
