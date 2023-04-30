package com.pro.junit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pro.junit.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
