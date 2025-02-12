package BookingService.BookingService.repository;

import BookingService.BookingService.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceEntityRepository extends JpaRepository<ServiceEntity, Long> {
}