package com.example.book_store.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.book_store.dto.UserDTO;
import com.example.book_store.dto.UserRegisterRequest;
import com.example.book_store.entity.Role;
import com.example.book_store.service.UserService;

import java.util.List;

/**
 * Controller quản lý người dùng (User)
 * Admin có thể xem danh sách, xem chi tiết, thêm, sửa, xóa user.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // Inject UserService qua constructor
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 🧩 ADMIN tạo tài khoản ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register-admin")
    public ResponseEntity<UserDTO> registerAdmin(@RequestBody UserRegisterRequest request) {
        UserDTO dto = new UserDTO(null, request.getUsername(), request.getEmail(), Role.ROLE_ADMIN);
        return ResponseEntity.ok(userService.register(dto, request.getPassword()));
    }

    // 🧩 USER tự đăng ký
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserRegisterRequest request) {
        UserDTO dto = new UserDTO(null, request.getUsername(), request.getEmail(), Role.ROLE_USER);
        return ResponseEntity.ok(userService.register(dto, request.getPassword()));
    }

    // 🧩 ADMIN tạo user mới (tùy role)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserRegisterRequest request) {
        UserDTO dto = new UserDTO(null, request.getUsername(), request.getEmail(), request.getRole());
        return ResponseEntity.ok(userService.register(dto, request.getPassword()));
    }

    // 🧩 Lấy tất cả user
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // 🧩 Xem chi tiết user theo id
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        UserDTO dto = userService.getUserById(id);
        return ResponseEntity.ok(dto);
    }

    // 🧩 Cập nhật user
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO dto) {
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    // 🧩 Xóa user
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
