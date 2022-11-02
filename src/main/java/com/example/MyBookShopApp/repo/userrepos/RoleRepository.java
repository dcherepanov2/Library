package com.example.MyBookShopApp.repo.userrepos;

import com.example.MyBookShopApp.data.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
