package com.taskmanager.app;

import com.taskmanager.app.model.Role;
import com.taskmanager.app.model.User;
import com.taskmanager.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
public class AppApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
		SpringApplication.run(AppApplication.class, args);

	}

	public void run(String... args){
		User adminAccount = userRepository.findByRole(Role.ADMIN);
		if (null == adminAccount){
			User user = new User();
			user.setUsername("admin");
			user.setEmail("admin@gmail.com");
			user.setPassword(new BCryptPasswordEncoder().encode("email"));
			user.setRole(Role.ADMIN);
			userRepository.save(user);
		}
	}
}
