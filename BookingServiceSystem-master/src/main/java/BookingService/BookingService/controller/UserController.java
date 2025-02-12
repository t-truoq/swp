package BookingService.BookingService.controller;


import BookingService.BookingService.dto.request.ApiResponse;
import BookingService.BookingService.dto.request.UserCreationRequest;
import BookingService.BookingService.dto.request.UserUpdateRequest;
import BookingService.BookingService.dto.response.UserResponse;
import BookingService.BookingService.entity.User;
import BookingService.BookingService.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import BookingService.BookingService.dto.request.ApiResponse;
import BookingService.BookingService.dto.request.UserCreationRequest;
import BookingService.BookingService.dto.request.UserUpdateRequest;
import BookingService.BookingService.dto.response.UserResponse;
import BookingService.BookingService.entity.User;
import BookingService.BookingService.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    // Tạo user - mở public (không cần token)
    @PostMapping
    public ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(request));
        return apiResponse;
    }

    // GET list user - chỉ ADMIN truy cập (đã cấu hình trong SecurityConfig)
    @GetMapping
    public ApiResponse<List<UserResponse>> getUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Current user: {}", authentication.getName());
        authentication.getAuthorities().forEach(a -> log.info(a.getAuthority()));

        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAllUsers())
                .build();
    }

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable("userId") Long userId) {
        return userService.getUser(userId);
    }

    @PutMapping("/{userId}")
    public UserResponse updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return "User deleted successfully!";
    }
}
