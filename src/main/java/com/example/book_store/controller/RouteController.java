package com.example.book_store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController // Đánh dấu đây là REST Controller, trả dữ liệu JSON
@RequestMapping("/routes") // Đường dẫn gốc cho controller này
public class RouteController {

    @Autowired // Tự động inject ApplicationContext (chứa toàn bộ bean trong Spring)
    private ApplicationContext applicationContext;

    // === API LẤY DANH SÁCH TẤT CẢ ROUTE (ĐƯỜNG DẪN API) ===
    @GetMapping // Gửi GET /routes
    public List<String> listAllRoutes() {
        // Lấy bean "requestMappingHandlerMapping" – nơi Spring lưu toàn bộ mapping (URL → controller method)
        RequestMappingHandlerMapping mapping =
                applicationContext.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);

        // Lấy danh sách các cặp RequestMappingInfo (route info) và HandlerMethod (hàm xử lý)
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = mapping.getHandlerMethods();

        List<String> paths = new ArrayList<>();

        // Duyệt qua từng RequestMappingInfo để lấy ra danh sách URL pattern (ví dụ: /api/products, /api/orders/{id}, ...)
        for (RequestMappingInfo info : handlerMethods.keySet()) {
            Set<String> patterns = info.getPatternValues(); // Lấy tất cả đường dẫn của route
            paths.addAll(patterns); // Thêm vào danh sách
        }

        // Loại bỏ trùng lặp, sắp xếp và trả về danh sách
        return paths.stream().distinct().sorted().toList();
    }
}
