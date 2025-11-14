"# book_store_backend" 

Backend project for Book Store application.  
Built with **Java Spring Boot**.
---
## 🚀 Cài đặt & chạy project
### 1. Clone repository
```bash
git clone https://github.com/vieneric77/book_store_backend.git
cd book_store_backend
2. Cài đặt Maven dependencies
bash
Copy code
mvn clean install
3. Cấu hình database
Mở file src/main/resources/application.properties và sửa thông tin database:
properties
Copy code
spring.datasource.url=jdbc:mysql://localhost:3306/book_store
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
Project này dùng MySQL. Bạn có thể tạo database book_store_db trước.
4. Chạy project
bash
Copy code
mvn spring-boot:run
Backend sẽ chạy mặc định trên: http://localhost:8080
📦 Seed Data / Thử nghiệm
Nếu bạn muốn có dữ liệu thử nghiệm, có thể chạy class DataInitializer.java đã tạo sẵn.
Nó sẽ thêm sẵn:
Users: admin, user
Categories: Fiction, Science, etc.
Products: sách mẫu
Roles: ROLE_ADMIN, ROLE_USER
Data sẽ được insert khi project chạy lần đầu.
📝 API Documentation (cơ bản)
Auth
Method	URL	Body	Description
POST	/api/auth/login	{username, password}	Login, trả về JWT token
POST	/api/auth/register	{username, password, email}	Đăng ký user mới
Users
Method	URL	Description
GET	/api/users	Lấy danh sách người dùng
GET	/api/users/{id}	Lấy thông tin user theo id
Products
Method	URL	Description
GET	/api/products	Lấy danh sách sản phẩm
POST	/api/products	Thêm sản phẩm mới (admin)
Cart
Method	URL	Description
GET	/api/carts/{userId}	Lấy giỏ hàng của user
POST	/api/carts	Thêm sản phẩm vào giỏ
Orders
Method	URL	Description
GET	/api/orders	Lấy danh sách đơn hàng
POST	/api/orders	Tạo đơn hàng mới
Lưu ý: Các endpoint bảo mật yêu cầu gửi JWT token trong header Authorization: Bearer <token>.
⚡ Công cụ & công nghệ
Java 17
Spring Boot 3.x
Maven
MySQL
JWT Authentication
🛠 Thêm
Chạy test:
bash
Copy code
mvn test
Build jar:
bash
Copy code
mvn package
java -jar target/book_store_backend-0.0.1-SNAPSHOT.jar
yaml
Copy code
---






You said:
