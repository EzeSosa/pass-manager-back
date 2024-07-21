package com.esosa.pass_manager.data.repositories;

import com.esosa.pass_manager.data.model.Password;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IPasswordRepository extends JpaRepository<Password, UUID> {}