package com.github.eric.course.dao;

import com.github.eric.course.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface RoleDao extends JpaRepository<Role, Integer> {
}
