package BookingService.BookingService.controller;

import BookingService.BookingService.entity.Image;
import BookingService.BookingService.entity.ServiceEntity;
import BookingService.BookingService.service.ServiceEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceEntityController {

    private final ServiceEntityService serviceEntityService;

    // =============== CRUD Dịch Vụ ===============
    @PostMapping
    public ResponseEntity<ServiceEntity> createService(@RequestBody ServiceEntity service) {
        ServiceEntity created = serviceEntityService.createService(service);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<ServiceEntity>> getAllServices() {
        List<ServiceEntity> list = serviceEntityService.getAllServices();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceEntity> getServiceById(@PathVariable Long id) {
        ServiceEntity service = serviceEntityService.getServiceById(id);
        return ResponseEntity.ok(service);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceEntity> updateService(
            @PathVariable Long id,
            @RequestBody ServiceEntity updated
    ) {
        ServiceEntity result = serviceEntityService.updateService(id, updated);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        serviceEntityService.deleteService(id);
        return ResponseEntity.noContent().build();
    }

    // =============== Upload ảnh (URL) ===============
    /**
     * Thêm ảnh vào service (chỉ lưu URL)
     * Gửi yêu cầu: POST /api/services/{serviceId}/images?url=...
     *
     * Hoặc bạn có thể @RequestParam("url") / @RequestBody JSON...
     */
    @PostMapping("/{serviceId}/images")
    public ResponseEntity<Image> addImageToService(
            @PathVariable Long serviceId,
            @RequestParam("url") String imageUrl
    ) {
        Image savedImage = serviceEntityService.addImageToService(serviceId, imageUrl);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedImage);
    }
}
