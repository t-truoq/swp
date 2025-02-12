package BookingService.BookingService.repository;


import BookingService.BookingService.entity.Booking;
import BookingService.BookingService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}