package BookingService.BookingService.service;

import BookingService.BookingService.entity.Image;
import BookingService.BookingService.entity.ServiceEntity;
import BookingService.BookingService.exception.AppException;
import BookingService.BookingService.exception.ErrorCode;
import BookingService.BookingService.repository.ImageRepository;
import BookingService.BookingService.repository.ServiceEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ServiceEntityService {

    private final ServiceEntityRepository serviceRepository;
    private final ImageRepository imageRepository;

    // Lấy service theo ID
    public ServiceEntity getServiceById(Long id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_EXISTED));
    }

    /**
     * Thêm một ảnh (URL) vào service.
     * Giả sử chúng ta đã upload ảnh lên CDN/S3
     * và chỉ cần lưu link vào DB.
     */
    public Image addImageToService(Long serviceId, String imageUrl) {
        // Tìm service
        ServiceEntity service = getServiceById(serviceId);

        // Tạo đối tượng Image
        Image image = Image.builder()
                .url(imageUrl)
                .createdAt(LocalDateTime.now())
                .service(service)
                .build();

        // Lưu vào DB
        return imageRepository.save(image);
    }

    // Các hàm CRUD khác cho ServiceEntity
    // (create, update, delete service)...

    public ServiceEntity createService(ServiceEntity service) {
        service.setCreatedAt(LocalDateTime.now());
        service.setUpdatedAt(LocalDateTime.now());
        return serviceRepository.save(service);
    }

    public ServiceEntity updateService(Long id, ServiceEntity updated) {
        ServiceEntity existing = getServiceById(id);
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setPrice(updated.getPrice());
        existing.setDuration(updated.getDuration());
        existing.setUpdatedAt(LocalDateTime.now());
        return serviceRepository.save(existing);
    }

    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }

    public List<ServiceEntity> getAllServices() {
        return serviceRepository.findAll();
    }
}
