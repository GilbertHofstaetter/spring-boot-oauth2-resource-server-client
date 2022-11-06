package com.gho.OAuth2ResourceServerClient.repository;

import com.gho.OAuth2ResourceServerClient.obj.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
