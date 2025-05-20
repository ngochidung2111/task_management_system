# Task Management Application

## Mô Tả Dự Án

Ứng dụng Quản Lý Công Việc (Task Management Application) giúp người dùng tạo, theo dõi và quản lý các công việc cá nhân. Ứng dụng cung cấp tính năng nhắc nhở qua email ) để người dùng không bỏ sót công việc và hoàn thành đúng hạn.

## Tính Năng

- **Quản Lý Công Việc**:
  - Tạo công việc mới với thông tin như tiêu đề, mô tả và thời gian hoàn thành.
  - Cập nhật và xóa công việc.
  - Lọc và tìm kiếm công việc theo tình trạng (hoàn thành, chưa hoàn thành).

- **Thông Báo và Nhắc Nhở**:
  - Gửi email nhắc nhở về công việc mới hoặc sắp đến hạn.

- **Bảo Mật và Phân Quyền**:
  - Phân quyền người dùng (Admin, User).
  - Bảo vệ các endpoint API và dữ liệu người dùng thông qua **Spring Security**.

## Công Nghệ Sử Dụng

- **Spring Boot**: Framework phát triển ứng dụng backend và cung cấp các RESTful APIs.
- **Spring Data JPA**: Quản lý cơ sở dữ liệu và lưu trữ công việc, người dùng.
- **Spring Security**: Bảo mật ứng dụng và phân quyền người dùng.
- **JavaMailSender**: Gửi email thông báo nhắc nhở cho người dùng.
- **MySQL**: Cơ sở dữ liệu lưu trữ thông tin công việc và người dùng.

## Link demo: https://task-management-system-fe-xqh9.vercel.app/
