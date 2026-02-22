package com.revworkforce.service.impl;

import com.revworkforce.entity.Holiday;
import com.revworkforce.repository.HolidayRepository;
import com.revworkforce.service.HolidayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HolidayServiceImpl implements HolidayService {

    private final HolidayRepository holidayRepository;

    @Override
    public Holiday createHoliday(Holiday holiday) {
        return holidayRepository.save(holiday);
    }

    @Override
    public void deleteHoliday(Long id) {
        holidayRepository.deleteById(id);
    }

    @Override
    public List<Holiday> getAllHolidays() {
        return holidayRepository.findAll();
    }

    @Override
    public List<Holiday> getHolidaysByYear(int year) {
        LocalDate start = LocalDate.of(year, 1, 1);
        LocalDate end = LocalDate.of(year, 12, 31);
        return holidayRepository.findByDateBetween(start, end);
    }
}