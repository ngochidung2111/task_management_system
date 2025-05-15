package com.taskmanager.app.service;



import com.taskmanager.app.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskReminderService {

    @Autowired
    private TaskService taskService;

    @Autowired
    private EmailNotificationService emailNotificationService;
    // Xây dựng thông điệp email đẹp hơn
    private String buildEmailMessage(Task task) {
        String taskId = task.getId().toString();  // Get task ID as string for URL parameter

        return "<html>"
                + "<head><style>"
                + "body { font-family: Arial, sans-serif; color: #333; line-height: 1.6; }"
                + ".container { background-color: #f9f9f9; padding: 20px; border-radius: 8px; max-width: 600px; margin: auto; }"
                + ".header { background-color: #4CAF50; color: white; padding: 10px 0; text-align: center; border-radius: 8px 8px 0 0; }"
                + ".footer { font-size: 0.9em; color: #777; text-align: center; margin-top: 20px; }"
                + ".highlight { color: #ff5733; font-weight: bold; }"
                + ".button-container { text-align: center; margin-top: 20px; }"  // Add container for centering
                + ".button { background-color: #4CAF50; color: white; padding: 10px 20px; text-align: center; border: none; cursor: pointer; font-size: 16px; border-radius: 5px; text-decoration: none; }"
                + ".button:hover { background-color: #45a049; }"
                + "</style></head>"
                + "<body>"
                + "<div class='container'>"
                + "<div class='header'>"
                + "<h2>Nhắc nhở công việc sắp đến hạn</h2>"
                + "</div>"
                + "<p>Chào bạn <b>" + "</b>,</p>"
                + "<p>Chúng tôi muốn nhắc bạn rằng công việc <span class='highlight'>\"" + task.getTitle() + "\"</span> của bạn sẽ hết hạn trong vòng 1 giờ nữa.</p>"
                + "<p>Hãy chắc chắn rằng bạn đã hoàn thành công việc này trong thời gian quy định để tránh trì hoãn hoặc ảnh hưởng đến tiến độ chung.</p>"
                + "<p>Để gia hạn thời gian hoàn thành công việc thêm 1 giờ, vui lòng nhấn vào nút bên dưới:</p>"
                + "<div class='button-container'>"
                + "<a href='http://localhost:5173/login' class='button'>Vào trang chủ</a>"
                + "</div>"
                + "<p>Chúng tôi luôn ở đây để hỗ trợ bạn. Nếu có bất kỳ câu hỏi nào, vui lòng liên hệ với chúng tôi.</p>"
                + "<div class='footer'>"
                + "<p>Chúc bạn một ngày làm việc hiệu quả!</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";
    }

    // Phương thức gửi nhắc nhở cho công việc 1 giờ trước khi hết hạn
    @Scheduled(cron = "0 0/1 * * * ?")  // Thực thi mỗi 1 phút
    public void sendTaskReminders() {
        // Lấy tất cả công việc mà thời gian hết hạn cách hiện tại dưới 1 giờ và chưa gửi thông báo
        List<Task> tasks = taskService.getTasksDueSoon();
        System.out.println(LocalDateTime.now());
        for (Task task : tasks) {
            // Nếu công việc còn lại ít hơn 1 giờ để đến hạn
            if (task.getDueDate().isBefore(LocalDateTime.now().plusHours(1))) {

                // Gửi email nhắc nhở
                String emailMessage = buildEmailMessage(task);
                emailNotificationService.sendReminderEmail(task.getUser().getEmail(), "Nhắc nhở công việc", emailMessage);

                // Cập nhật trạng thái đã gửi thông báo cho công việc
                taskService.updateTaskNotified(task.getId());
            }
        }
    }
}

