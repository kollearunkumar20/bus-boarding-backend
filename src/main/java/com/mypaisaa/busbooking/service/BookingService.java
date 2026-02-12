package com.mypaisaa.busbooking.service;

import com.mypaisaa.busbooking.dto.BookingRequest;
import com.mypaisaa.busbooking.exception.BusinessException;
import com.mypaisaa.busbooking.model.Booking;
import com.mypaisaa.busbooking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository repository;

    public Booking createBooking(BookingRequest request) {
        List<Booking> sameUserBookings =
                repository.findByTravelDateAndMobileNumber(
                        request.getTravelDate(),
                        request.getMobileNumber()
                );

        int totalSeatsBooked = sameUserBookings.stream()
                .mapToInt(b -> b.getSeats().size())
                .sum();

        if (totalSeatsBooked + request.getSeats().size() > 6) {
            throw new BusinessException("Maximum 6 seats allowed per mobile per day");
        }
        List<Booking> allBookings = repository.findByTravelDate(request.getTravelDate());

        Set<String> alreadyBookedSeats = allBookings.stream()
                .flatMap(b -> b.getSeats().stream())
                .collect(Collectors.toSet());

        for (String seat : request.getSeats()) {
            if (alreadyBookedSeats.contains(seat)) {
                throw new BusinessException("Seat already booked: " + seat);
            }
        }

        Booking booking = new Booking();
        booking.setId(UUID.randomUUID().toString().substring(0,8));
        booking.setTravelDate(request.getTravelDate());
        booking.setMobileNumber(request.getMobileNumber());
        booking.setSeats(request.getSeats());
        booking.setBoarded(false);

        return repository.save(booking);
    }

    public List<Booking> getBookingsByDate(java.time.LocalDate date) {
        return repository.findByTravelDate(date);
    }

    public void markBoarded(String id) {
        Booking booking = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Booking not found"));

        booking.setBoarded(true);
        repository.save(booking);
    }
}
