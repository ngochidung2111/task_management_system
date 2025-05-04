package com.taskmanager.app.repository;

import com.taskmanager.app.model.Role;
import com.taskmanager.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByUsername(String username);

    User findByRole(Role role);
}
