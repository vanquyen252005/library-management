
# **Library Management Application**

## **1. Giới thiệu**

### 1.1 Mục đích  
Đây là một ứng dụng quản lý thư viện được xây dựng theo mô hình lập trình hướng đối tượng (OOP). Ứng dụng cho phép người dùng quản lý sách, độc giả và thực hiện các giao dịch mượn/trả sách.  

### 1.2 Tính năng chính  
- **Quản lý thông tin thư viện**:  
   - Cho phép người quản lý thư viện quản lý thông tin sách và độc giả.
- **Dịch vụ dành cho độc giả**:  
   - Độc giả có thể khám phá danh mục sách, mượn/trả sách, đăng ký tài khoản, cập nhật thông tin cá nhân, và theo dõi lịch sử mượn/trả sách.  

---

## **2. Công nghệ sử dụng**

### 2.1 Ngôn ngữ lập trình  
- **Java**

### 2.2 Thư viện/Framework  
- **JavaFX**: Giao diện người dùng.  
- **JDBC**: Kết nối cơ sở dữ liệu.  
- **JSON, API**: Xử lý dữ liệu và tích hợp tính năng nâng cao.  

### 2.3 Cơ sở dữ liệu  
- **MySQL**  

### 2.4 Công cụ hỗ trợ  
- **SceneBuilder**: Thiết kế giao diện.  
- **MySQL Workbench**: Quản lý cơ sở dữ liệu.  

---

## **3. Hướng dẫn cài đặt**

### 3.1 Yêu cầu hệ thống  

- **Phiên bản JDK**: Java JDK 17 hoặc Java JDK 22.  
- **Các thư viện cần cài đặt**:  
  - JavaFX  
  - JDBC  
  - JSON  
  - API  

### 3.2 Cách chạy ứng dụng  

1. **Tải mã nguồn từ GitHub**  
   - Truy cập [GitHub Repository](https://github.com/vanquyen252005/library-management).  
   - Sao chép liên kết HTTPS của repository.

2. **Tải mã nguồn về máy**  
   - Tạo một thư mục rỗng trên máy tính.  
   - Mở thư mục và nhấp chuột phải, chọn **Open in Terminal**.  
   - Gõ lệnh sau và dán liên kết GitHub đã sao chép:  
     ```bash
     git clone <link_github>
     ```
   - Nhấn `Enter`.  

3. **Mở dự án bằng IntelliJ IDEA**  
   - Mở IntelliJ IDEA, chọn **Open Project**, và chọn thư mục dự án vừa tải.  
   - Chạy ứng dụng thông qua IntelliJ IDEA.  

---

## **4. Hướng dẫn sử dụng**

### 4.1 Giao diện dành cho người quản lý (Admin)  
- **Chức năng chính**:  
  - Quản lý thông tin sách(tìm kiếm sách, thêm sách xóa sách, thống kê số lượng sách) 
  - Quản lý thông tin độc giả(những cuốn sách mà người dùng đang mượn,đã trả).  
  - Theo dõi lịch sử mượn/trả sách(Quét Qr để hiển thị thông tin sách) 

### 4.2 Giao diện dành cho người dùng (Student)  
- **Chức năng chính**:  
  - Khám phá danh mục sách.  
  - Mượn sách và trả sách.  
  - Đăng ký tài khoản, cập nhật thông tin cá nhân.  
  - Theo dõi lịch sử mượn/trả sách của mình.  
- **Chức năng phụ**:
  -  Vừa đọc sách vừa nghe nhạc
---

## **5. Thiết kế kiến trúc**

### 5.1 Lập trình hướng đối tượng (OOP)  
Ứng dụng được thiết kế theo nguyên lý OOP với các lớp chính:  
- **Admin**: Quản lý thông tin độc giả và sách.  
- **Student**: Thực hiện các chức năng của độc giả.  
- **Book**: Lưu trữ dữ liệu và các thuộc tính của sách.  

### 5.2 Sơ đồ thừa kế  
Dưới đây là sơ đồ thừa kế của các lớp:  
```plaintext
           Người dùng
              |
   -------------------------
   |                       |
Admin                Student
   |
 Book
```

---

## **6. Ghi chú**

### 6.1 Lưu ý  
- **Hạn chế**:  
  - Hệ thống cần kết nối cơ sở dữ liệu đúng phiên bản và cấu hình đúng các thư viện hỗ trợ.

### 6.2 Định hướng phát triển  
- Thêm chức năng tìm kiếm nâng cao (theo thể loại, tác giả).  
- Tích hợp API để liên kết với hệ thống quản lý sách trực tuyến.  
- Hỗ trợ tính năng nhắc nhở qua email khi gần hết hạn trả sách.  

### 6.3 Tài liệu tham khảo  
- [JavaFX Documentation](https://openjfx.io/)  
- [MySQL Documentation](https://dev.mysql.com/doc/)  
