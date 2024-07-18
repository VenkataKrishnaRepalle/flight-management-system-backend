package com.learning.fms.repository;

import com.learning.fms.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {
    List<UserRole> getByUser_Uuid(UUID userUuid);
}
