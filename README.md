EduLearn - Hệ thống Quản lý Học tập (LMS)
EduLearn là một hệ thống Quản lý Học tập (LMS) hiện đại, được xây dựng với kiến trúc Microservices mạnh mẽ, cung cấp trải nghiệm học tập và quản lý khóa học toàn diện cho cả học viên và giảng viên.

1. Công nghệ & Kiến trúc
Dự án được phát triển sử dụng bộ công nghệ hiện đại, tập trung vào khả năng mở rộng và hiệu năng:
1.1 Frontend
    - Next.js (React): Xây dựng giao diện người dùng nhanh, linh hoạt và tối ưu SEO.
    - Backend (Microservices)
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
| **payment-service** | Xử lý giao dịch thanh toán, tích hợp các cổng thanh toán VNPAY, MoMo, ZaloPay. |
| **enrollment-service** | Quản lý việc ghi danh học viên vào khóa học và theo dõi tiến độ. |
| **quiz-service** | Xây dựng, quản lý và xử lý các bài kiểm tra, câu hỏi và lưu trữ kết quả. |
| **review-service** | Quản lý đánh giá và xếp hạng (rating) của học viên dành cho khóa học. |
| **notification-service** | Gửi thông báo (email, tin nhắn) và xử lý sự kiện bất đồng bộ qua Kafka. |
| **aggregation-service** | Tập hợp dữ liệu từ nhiều dịch vụ khác nhau và sử dụng Redis để lưu cache nhằm tối ưu hiệu suất. |
| **common-lib** | Thư viện chung chứa các lớp, tiện ích, hằng số, và cấu hình được chia sẻ giữa các Microservices. |


3. Giao diện Người dùng 
3.1 Giao diện người dùng 
Hiển thị thể loại và danh sách khóa học
<img src = "https://drive.google.com/file/d/1yuYiQQWHsdbWBDMboJfKCuPIyxtc33wL/view?usp=sharing">
<img src = "https://drive.google.com/file/d/1IyNtkgWOKO1qfjy3dZNQOoJfeYIn3Tp6/view?usp=sharing">

3.2 Đăng ký & Đăng nhập
Người dùng có thể tạo tài khoản mới hoặc đăng nhập bằng tài khoản hiện có, hoặc qua Google.
<img src = "https://drive.google.com/file/d/1E_92_VP3d1uefpQoTRNki2MiePUoPUMn/view?usp=sharing">
<img src = "https://drive.google.com/file/d/1cgvN-x-bpLVdBIfUOry8ANLPpHFG4qpS/view?usp=sharing">


3.3 Chi tiết Khóa học & Mua hàng
Hiển thị thông tin khóa học, đánh giá và quy trình thanh toán tích hợp.
    + Chi tiết Khóa học
    <img src = "https://drive.google.com/file/d/104NU0ZHRNgDdI1tZswsGQDcDD5ym6fIY/view?usp=sharing">

    + Thanh toán
    <img src = "https://drive.google.com/file/d/1p0wrOI0DoZC00RWBcwde7QZgPIXWzZHw/view?usp=sharing">


3.4 Trải nghiệm Học tập
Giao diện trực quan để theo dõi bài học, nội dung, và cấu trúc kiến trúc ứng dụng.
<img src = "https://drive.google.com/file/d/1G4KAtU9BC3Dyxr5ujfHsu3V_ZZ4_HvUf/view?usp=sharing">

3.5 Bài kiểm tra (Quiz)
Hệ thống cung cấp tính năng làm bài kiểm tra để đánh giá kiến thức của học viên.
<img src = "https://drive.google.com/file/d/1HmqbWLRwiTsVM_ADO8p3lzT38vOBM-5O/view?usp=sharing">
<img src = "https://drive.google.com/file/d/1CjCgPc3kXI7hKHqlXmFsO08a_fUhziaY/view?usp=sharing">


3.6. Hồ sơ Học viên
Học viên có thể xem thông tin cá nhân và quản lý các khóa học đã tham gia/hoàn thành.
<img src = "https://drive.google.com/file/d/12Ocda6OyLUVkeyhlkcrU6v8x3WzH2Xev/view?usp=sharing">

3.7. Quản lý Giảng viên
Giảng viên có giao diện riêng để theo dõi và quản lý học viên của mình.
<img src = "https://drive.google.com/file/d/1YMJHjT6M1l5tpAq24sHy005EZpsw3e9A/view?usp=sharing">
<img src = "https://drive.google.com/file/d/1uLhSZDOsfh7Y8PDZvICg2FQ-CsQvSaNI/view?usp=sharing">

4. Cấu trúc Mã nguồn Backend
Dự án sử dụng cấu trúc thư mục rõ ràng để quản lý các Microservice.
<img src = "https://drive.google.com/file/d/1aQmI48Hg9RhkTbso37yiie_Yx_OsxFaM/view?usp=sharing">

5. Hướng dẫn Cài đặt & Khởi chạy
5.1 Clone repository: git clone https://github.com/TienDat-30404/learning_management_system.git
5.2 Khởi động các dịch vụ phụ (Kafka, Redis, PostgreSQL):Sử dụng tệp docker-compose.yml để khởi chạy các dịch vụ này : docker-compose up -d
5.3 Cấu hình: Cập nhật các tệp cấu hình (ví dụ: application.yml trong config-server) với các thông tin như JWT secret, VNPAY credentials và thông tin database.
5.4 Khởi chạy Backend (Spring Boot Microservices):Bạn có thể chạy từng service qua IDE hoặc sử dụng Maven
+ Chạy từng service
+ cd springboot/[service-name] -> ./run.ps1 hoặc mvn spring-boot:run
+ Đảm bảo các dịch vụ cấu hình và phát hiện (Config-Server, Discovery-Eureka) được khởi động trước tiên.
5.5 Khởi chạy Frontend
npm install
npm run dev