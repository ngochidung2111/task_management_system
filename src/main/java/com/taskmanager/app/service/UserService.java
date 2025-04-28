package com.taskmanager.app.service;

import com.taskmanager.app.model.User;
import com.taskmanager.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public User getUserById(Long id){
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }
    // Tạo người dùng mới
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Cập nhật người dùng
    public User updateUser(Long id, User user) {
        if (userRepository.existsById(id)) {
            user.setId(id);  // Đảm bảo giữ ID cũ
            return userRepository.save(user);
        }
        return null;  // Trả về null nếu không tìm thấy người dùng
    }

    // Xóa người dùng
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;  // Trả về false nếu không tìm thấy người dùng
    }
}
