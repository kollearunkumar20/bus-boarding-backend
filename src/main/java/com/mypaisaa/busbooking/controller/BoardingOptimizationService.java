package com.mypaisaa.busbooking.service;

import com.mypaisaa.busbooking.model.Booking;
import com.mypaisaa.busbooking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardingOptimizationService {

    private final BookingRepository repository;

    // Greedy: farthest seat first
    public List<Booking> getOptimalBoarding(LocalDate date) {

        List<Booking> bookings = repository.findByTravelDate(date);

        return bookings.stream()
                .sorted(Comparator.comparingInt(this::getFarthestSeat).reversed())
                .toList();
    }

    private int getFarthestSeat(Booking booking) {
        return booking.getSeats().stream()
                .mapToInt(seat -> seat.charAt(0) - 'A')
                .max()
                .orElse(0);
    }
}
