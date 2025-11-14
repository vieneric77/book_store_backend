package com.example.book_store.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import com.example.book_store.dto.LoginRequest;
import com.example.book_store.service.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    // Inject AuthService qua constructor (Spring tự động quản lý dependency)
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * API Đăng nhập (Login)
     * Nhận username + password từ client, xác thực và trả về token JWT nếu hợp lệ.
     *
     * @param request đối tượng chứa thông tin đăng nhập (username, password)
     * @return ResponseEntity chứa JWT token và thông tin user
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            // Gọi AuthService để xác thực và lấy token + user info
            Map<String, Object> response = authService.login(
                    request.getUsername(),
                    request.getPassword()
            );

            // Trả về phản hồi HTTP 200 OK + header chứa token JWT
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + response.get("token"))
                    .body(response);

        } catch (BadCredentialsException e) {
            // Nếu username hoặc password sai → trả HTTP 401 (Unauthorized)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }
    }
}
