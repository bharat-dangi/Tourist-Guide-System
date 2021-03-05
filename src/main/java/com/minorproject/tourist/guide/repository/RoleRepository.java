package com.minorproject.tourist.guide.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.minorproject.tourist.guide.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	public Role findByRole(String role);
}
