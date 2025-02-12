package BookingService.BookingService.service;


import BookingService.BookingService.dto.request.BookingRequest;
import BookingService.BookingService.dto.response.BookingResponse;
import BookingService.BookingService.entity.Booking;
import BookingService.BookingService.entity.User;
import BookingService.BookingService.exception.AppException;
import BookingService.BookingService.exception.ErrorCode;
import BookingService.BookingService.mapper.BookingMapper;
import BookingService.BookingService.repository.BookingRepository;
import BookingService.BookingService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final BookingMapper bookingMapper;

    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(bookingMapper::toResponse)
                .collect(Collectors.toList());
    }

    public Optional<BookingResponse> getBookingById(Long id) {
        return bookingRepository.findById(id).map(bookingMapper::toResponse);
    }

    public BookingResponse createBooking(BookingRequest request) {
        User customer = userRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        User specialist = userRepository.findById(request.getSpecialistId())
                .orElseThrow(() -> new AppException(ErrorCode.SKIN_THERAPIST_NOT_EXISTED));

        Booking booking = bookingMapper.toEntity(request);
        bookingMapper.setUserEntities(booking, customer, specialist);

        booking.setCreatedAt(LocalDateTime.now());
        booking.setUpdatedAt(LocalDateTime.now());

        Booking savedBooking = bookingRepository.save(booking);
        return bookingMapper.toResponse(savedBooking);
    }

    public BookingResponse updateBooking(Long id, BookingRequest request) {
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_EXISTED));

        User customer = userRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        User specialist = userRepository.findById(request.getSpecialistId())
                .orElseThrow(() -> new AppException(ErrorCode.SKIN_THERAPIST_NOT_EXISTED));


        bookingMapper.setUserEntities(existingBooking, customer, specialist);
        existingBooking.setBookingDate(request.getBookingDate());
        existingBooking.setTimeSlot(request.getTimeSlot());
        existingBooking.setStatus(request.getStatus());
        existingBooking.setCheckInTime(request.getCheckInTime());
        existingBooking.setCheckOutTime(request.getCheckOutTime());
        existingBooking.setTotalPrice(request.getTotalPrice());
        existingBooking.setUpdatedAt(LocalDateTime.now());

        Booking updatedBooking = bookingRepository.save(existingBooking);
        return bookingMapper.toResponse(updatedBooking);
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}