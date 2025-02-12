package BookingService.BookingService.entity;

import BookingService.BookingService.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    Long bookingId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    User customer;

    @ManyToOne
    @JoinColumn(name = "specialist_id")
    User specialist;

    @Column(name = "booking_date")
    LocalDateTime bookingDate;

    @Column(name = "time_slot")
    String timeSlot;

    @Enumerated(EnumType.STRING)
    BookingStatus status;

    @Column(name = "check_in_time")
    LocalDateTime checkInTime;

    @Column(name = "check_out_time")
    LocalDateTime checkOutTime;

    @Column(name = "total_price")
    BigDecimal totalPrice;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;
}