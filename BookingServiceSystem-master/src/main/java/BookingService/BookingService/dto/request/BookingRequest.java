package BookingService.BookingService.dto.request;

import BookingService.BookingService.enums.BookingStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BookingRequest {

    @NotNull(message = "Customer ID cannot be null")
    private Long customerId;

    @NotNull(message = "Specialist ID cannot be null")
    private Long specialistId;

    @NotNull(message = "Booking date is required")
    private LocalDateTime bookingDate;
    private String timeSlot;
    private BigDecimal totalPrice;
}
