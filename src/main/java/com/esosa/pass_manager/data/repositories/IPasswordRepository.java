package com.esosa.pass_manager.data.repositories;

import com.esosa.pass_manager.data.model.Password;
import com.esosa.pass_manager.data.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IPasswordRepository extends JpaRepository<Password, UUID> {
    Page<Password> findByUser(PageRequest pageRequest, User user);
}