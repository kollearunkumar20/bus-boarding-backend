package com.mypaisaa.busbooking.controller;

import com.mypaisaa.busbooking.dto.BookingRequest;
import com.mypaisaa.busbooking.model.Booking;
import com.mypaisaa.busbooking.service.BookingService;
import com.mypaisaa.busbooking.service.BoardingOptimizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final BoardingOptimizationService boardingService;
    @PostMapping
    public Booking create(@Validated @RequestBody BookingRequest request) {
        return bookingService.createBooking(request);
    }

    @GetMapping("/{date}")
    public List<Booking> getByDate(@PathVariable LocalDate date) {
        return bookingService.getBookingsByDate(date);
    }
    @PatchMapping("/{id}/board")
    public String markBoarded(@PathVariable String id) {
        bookingService.markBoarded(id);
        return "Passenger boarded";
    }
    @GetMapping("/{date}/optimal")
    public List<Booking> optimalSequence(@PathVariable LocalDate date) {
        return boardingService.getOptimalBoarding(date);
    }
}
