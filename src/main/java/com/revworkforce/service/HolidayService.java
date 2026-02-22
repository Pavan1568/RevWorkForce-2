package com.revworkforce.service;

import com.revworkforce.entity.Holiday;

import java.time.LocalDate;
import java.util.List;

public interface HolidayService {

    Holiday createHoliday(Holiday holiday);

    void deleteHoliday(Long id);

    List<Holiday> getAllHolidays();

    List<Holiday> getHolidaysByYear(int year);

    Holiday updateHoliday(Long id, Holiday holiday);

}