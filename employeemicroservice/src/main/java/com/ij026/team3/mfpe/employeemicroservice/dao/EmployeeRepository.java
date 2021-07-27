package com.ij026.team3.mfpe.employeemicroservice.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ij026.team3.mfpe.employeemicroservice.model.Employee;

import lombok.Data;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
	Optional<Employee> findByEmpId(String empId);

}
