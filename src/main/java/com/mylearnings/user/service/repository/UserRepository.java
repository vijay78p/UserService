package com.mylearnings.user.service.repository;

import com.mylearnings.user.service.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}
