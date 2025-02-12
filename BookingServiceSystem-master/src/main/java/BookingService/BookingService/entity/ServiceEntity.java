package BookingService.BookingService.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    Long serviceId;

    String name;

    @Column(columnDefinition = "TEXT")
    String description;

    BigDecimal price;
    Integer duration;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    // Một dịch vụ có thể có nhiều hình ảnh
    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Image> images;
}
