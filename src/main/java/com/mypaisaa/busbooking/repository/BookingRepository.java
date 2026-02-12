package com.mypaisaa.busbooking.repository;

import com.mypaisaa.busbooking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, String> {
    List<Booking> findByTravelDate(LocalDate travelDate);
    List<Booking> findByTravelDateAndMobileNumber(LocalDate travelDate, String mobileNumber);
}
