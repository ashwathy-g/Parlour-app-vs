package com.example.ParlourApp.booking;

import com.example.ParlourApp.dto.BookingRequestDto;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;


@Service
@Slf4j
public class BookingService
{
    @Autowired
    private BookingRepository bookingRepository;

//    public void addBooking(BookingRequestDto bookingRequestDto)
//    {   BookingRegModel bookingRegModel1=new BookingRegModel();
//        bookingRegModel1.setUserId(bookingRequestDto.getUserId());
//        bookingRegModel1.setuserName()
//        bookingRepository.save(bookingRegModel);
//    }

    public List<BookingRegModel> getOneDayBookings(Long parlourId, LocalDate date) {
        return bookingRepository.findByParlourIdAndBookingDate(parlourId, date);
    }

    public List<BookingRegModel> getOneWeekBookings(Long parlourId, LocalDate date) {
        LocalDate startDate = date.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        LocalDate endDate = date.with(TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY));
        return bookingRepository.findBookingsWithinDateRange(parlourId, startDate, endDate);
    }

    public List<BookingRegModel> getOneMonthBookings(Long parlourId, LocalDate date) {
        LocalDate startDate = date.withDayOfMonth(1);
        LocalDate endDate = date.with(TemporalAdjusters.lastDayOfMonth());
        return bookingRepository.findBookingsWithinDateRange(parlourId, startDate, endDate);
    }

}