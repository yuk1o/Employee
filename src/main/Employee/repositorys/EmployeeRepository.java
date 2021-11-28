package com.yuk1of.Employee.repositorys;

import com.yuk1of.Employee.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
