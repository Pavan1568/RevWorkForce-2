package com.revworkforce.controller;

import com.revworkforce.entity.Holiday;
import com.revworkforce.service.HolidayService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/holidays")
@RequiredArgsConstructor
public class HolidayController {

    private final HolidayService holidayService;

    // Admin only
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Holiday createHoliday(@RequestBody Holiday holiday) {
        return holidayService.createHoliday(holiday);
    }

    // All roles
    @GetMapping
    public List<Holiday> getAllHolidays() {
        return holidayService.getAllHolidays();
    }

    @GetMapping("/year/{year}")
    public List<Holiday> getByYear(@PathVariable int year) {
        return holidayService.getHolidaysByYear(year);
    }

    // ✏️ Update holiday
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Holiday updateHoliday(@PathVariable Long id,
                                 @RequestBody Holiday holiday) {
        return holidayService.updateHoliday(id, holiday);
    }

    // ❌ Delete holiday
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteHoliday(@PathVariable Long id) {
        holidayService.deleteHoliday(id);
    }
}