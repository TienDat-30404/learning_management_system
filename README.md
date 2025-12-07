EduLearn - Hệ thống Quản lý Học tập (LMS)
EduLearn là một hệ thống Quản lý Học tập (LMS) hiện đại, được xây dựng với kiến trúc Microservices mạnh mẽ, cung cấp trải nghiệm học tập và quản lý khóa học toàn diện cho cả học viên và giảng viên.

1. Công nghệ & Kiến trúc
Dự án được phát triển sử dụng bộ công nghệ hiện đại, tập trung vào khả năng mở rộng và hiệu năng:
1.1 Frontend
    + Next.js (React): Xây dựng giao diện người dùng nhanh, linh hoạt và tối ưu SEO.
1.2 Backend
    + Spring Boot (Java): Khung công tác chính cho các dịch vụ.
    + Kiến trúc: Microservices (tối ưu hóa khả năng mở rộng và độc lập dịch vụ).
    + Quản lý Cổng (Gateway): API Gateway để định tuyến và bảo mật.
    + Đăng ký/Phát hiện Dịch vụ: Discovery-Eureka Server.
    + Cấu hình Tập trung: Config-Server.
    + Bảo mật: JWT (JSON Web Tokens) cho xác thực và ủy quyền.
    + Bộ nhớ đệm/Caching: Redis được sử dụng trong Aggregation-Service để tăng tốc độ truy cập dữ liệu thường xuyên.
    + Hàng đợi tin nhắn/Message Queue: Kafka được sử dụng cho các tác vụ bất đồng bộ như gửi thông báo (Notification-Service, Enrollment-Service).

2. Các Dịch vụ Microservices chính

| Dịch vụ | Chức năng |
| :--- | :--- |
| **api-gateway** | Định tuyến yêu cầu, bảo mật, và cân bằng tải (Load Balancing) đến các dịch vụ Microservices. |

| **discovery-eureka** | Máy chủ Đăng ký và Phát hiện Dịch vụ (Service Discovery) để các dịch vụ có thể tìm thấy nhau. |

| **config-server** | Quản lý cấu hình tập trung cho tất cả các Microservices. |

| **user-service** | Quản lý thông tin người dùng (Đăng ký, Đăng nhập, Hồ sơ, Xác thực vai trò Giảng viên/Học viên). |

| **course-service** | Quản lý thông tin khóa học, nội dung bài học, và tài liệu học tập. |

| **discount-service** | Quản lý các chương trình giảm giá, mã khuyến mãi và áp dụng chiết khấu. |

| **payment-service** | Xử lý giao dịch thanh toán, tích hợp các cổng thanh toán **VNPAY**, MoMo, ZaloPay. |

| **enrollment-service** | Quản lý việc ghi danh học viên vào khóa học và theo dõi tiến độ. |

| **quiz-service** | Xây dựng, quản lý và xử lý các bài kiểm tra, câu hỏi và lưu trữ kết quả. |

| **review-service** | Quản lý đánh giá và xếp hạng (rating) của học viên dành cho khóa học. |

| **notification-service** | Gửi thông báo (email, tin nhắn) và xử lý sự kiện bất đồng bộ qua **Kafka**. |

| **aggregation-service** | Tập hợp dữ liệu từ nhiều dịch vụ khác nhau và sử dụng **Redis** để lưu cache nhằm tối ưu hiệu suất. |

| **common-lib** | Thư viện chung chứa các lớp, tiện ích, hằng số, và cấu hình được chia sẻ giữa các Microservices. |


3. Giao diện Người dùng 
3.1 Giao diện người dùng 
Hiển thị thể loại và danh sách khóa học
<img src = "https://res.cloudinary.com/ddiljm2gn/image/upload/v1765127641/bav30xifj5giyixvqpoj.png">
<img src = "https://res.cloudinary.com/ddiljm2gn/image/upload/v1765127673/cmt70uzxxbbynxsjhpms.png">

3.2 Đăng ký & Đăng nhập
Người dùng có thể tạo tài khoản mới hoặc đăng nhập bằng tài khoản hiện có, hoặc qua Google.
<img src = "https://res.cloudinary.com/ddiljm2gn/image/upload/v1765127708/mup7401zkn2wq7t4z6r8.png">
<img src = "https://res.cloudinary.com/ddiljm2gn/image/upload/v1765127747/edsohdwsd8yehavxmmj7.png">


3.3 Chi tiết Khóa học & Mua hàng
Hiển thị thông tin khóa học, đánh giá và quy trình thanh toán tích hợp.
    + Chi tiết Khóa học
    <img src = "https://res.cloudinary.com/ddiljm2gn/image/upload/v1765128094/xcf6ptoob3pj6v5xrbbo.png">

    + Thanh toán
    <img src = "https://res.cloudinary.com/ddiljm2gn/image/upload/v1765128292/tzgazklcxitmngu5trhx.png">


3.4 Trải nghiệm Học tập
Giao diện trực quan để theo dõi bài học, nội dung, và cấu trúc kiến trúc ứng dụng.
<img src = "https://res.cloudinary.com/ddiljm2gn/image/upload/v1765128314/fqknkvvw5yoplny71fx8.png">

3.5 Bài kiểm tra (Quiz)
Hệ thống cung cấp tính năng làm bài kiểm tra để đánh giá kiến thức của học viên.
<img src = "https://res.cloudinary.com/ddiljm2gn/image/upload/v1765128335/qyrdrvxs9gzehxldkfe1.png">
<img src = "https://res.cloudinary.com/ddiljm2gn/image/upload/v1765128353/jee32jbvpla0xpd2irb6.png">


3.6. Hồ sơ Học viên
Học viên có thể xem thông tin cá nhân và quản lý các khóa học đã tham gia/hoàn thành.
<img src = "https://res.cloudinary.com/ddiljm2gn/image/upload/v1765128375/czw0ji2erixwd5szl9g5.png">

3.7. Quản lý Giảng viên
Giảng viên có giao diện riêng để theo dõi và quản lý học viên của mình.
<img src = "https://res.cloudinary.com/ddiljm2gn/image/upload/v1765128396/yr3ocvf5hpythcxzjcmz.png">
<img src = "https://res.cloudinary.com/ddiljm2gn/image/upload/v1765128415/iv2xzg9loysfwrec3tzx.png">

4. Cấu trúc Mã nguồn Backend
Dự án sử dụng cấu trúc thư mục rõ ràng để quản lý các Microservice.
<img src = "https://res.cloudinary.com/ddiljm2gn/image/upload/v1765128430/qxgfyzyyk7wq785gifv5.png">

## 5. Hướng dẫn Cài đặt & Khởi chạy

1.  **Clone repository:**
    git clone [https://github.com/TienDat-30404/learning_management_system.git](https://github.com/TienDat-30404/learning_management_system.git)

2.  **Khởi động các dịch vụ phụ (Kafka, Redis, PostgreSQL):**
    Sử dụng tệp `docker-compose.yml` để khởi chạy các dịch vụ này:
    ```bash
    docker-compose up -d
    ```

3.  **Cấu hình:**
    Cập nhật các tệp cấu hình (ví dụ: `application.yml` trong `config-server`) với các thông tin như JWT secret, VNPAY credentials và thông tin database.

4.  **Khởi chạy Backend (Spring Boot Microservices):**
    Bạn có thể chạy từng service qua IDE hoặc sử dụng Maven:
    * **Chạy từng service:**
        ```bash
        cd springboot/[service-name]
        ./run.ps1 hoặc mvn spring-boot:run
        ```
    * **Đảm bảo** các dịch vụ cấu hình và phát hiện (**Config-Server**, **Discovery-Eureka**) được khởi động trước tiên.

5.  **Khởi chạy Frontend:**
    ```bash
    npm install
    npm run dev
    ```