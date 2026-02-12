package com.mypaisaa.busbooking.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BookingRequest {

    @NotNull
    private LocalDate travelDate;

    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Size(min = 1, max = 6, message = "You can book maximum 6 seats")
    private List<String> seats;
}
