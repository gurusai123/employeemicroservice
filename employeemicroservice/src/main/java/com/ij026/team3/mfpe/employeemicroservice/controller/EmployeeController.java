package com.ij026.team3.mfpe.employeemicroservice.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ij026.team3.mfpe.employeemicroservice.dao.EmployeeRepository;
import com.ij026.team3.mfpe.employeemicroservice.model.Employee;
import com.ij026.team3.mfpe.employeemicroservice.model.Offer;
import com.ij026.team3.mfpe.employeemicroservice.service.EmployeeService;

@RestController
public class EmployeeController {
	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/test")
	public String test() {
		return ("test");
	}

	@GetMapping("/employeeOffers/{empId}")
	public Map<String, Object> getEmployeeOffers(@PathVariable String empId) {
		return employeeService.offersByEmployee(empId);
	}

	@GetMapping("/offers/by-most-liked")
	public List<Offer> getMostLikedOffers(@RequestParam(required = true) String empId) {
		return (employeeService.topLikedOffersByEmployee(empId));
	}

	@GetMapping("/employee")
	public ResponseEntity<Employee> viewEmployeeProfile(@RequestParam(required = true) String empId) {
		Optional<Employee> viewProfile = employeeService.viewProfile(empId);
		if (viewProfile.isPresent()) {
			return ResponseEntity.ok(viewProfile.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping("/employees")
	public List<Employee> viewAllEmployee() {
		return employeeRepository.findAll();
	}

}
