package com.mypaisaa.busbooking.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Booking {

    @Id
    private String id;

    private LocalDate travelDate;

    private String mobileNumber;

    @ElementCollection
    private List<String> seats;

    private boolean boarded = false;
}
