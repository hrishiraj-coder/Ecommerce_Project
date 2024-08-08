package com.ums.repository;

import com.ums.entites.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {

 Role findByRoleCode(String roleCode);
}
