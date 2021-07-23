package com.ij026.team3.mfpe.employeemicroservice.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ij026.team3.mfpe.employeemicroservice.dao.EmployeeRepository;
import com.ij026.team3.mfpe.employeemicroservice.exception.NoSuchEmpIdException;
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
	private ConcurrentHashMap<String, Object> empIdCache = new ConcurrentHashMap<>();

	public EmployeeController() {
		empIdCache.put("guru", new Object());
		empIdCache.put("nikky", new Object());
		empIdCache.put("subsa", new Object());
		empIdCache.put("rish", new Object());
		empIdCache.put("ujjw", new Object());
	}


	@GetMapping("/employeeOffers/{empId}")
	public Map<String, Object> getEmployeeOffers(@PathVariable String empId) {
		if(empIdCache.contains(empId)) {
		return employeeService.offersByEmployee(empId);
		}
		else {
			throw new NoSuchEmpIdException("empid " + empId + " is invalid");
		}
	}

	@GetMapping("/offers/by-most-liked")
	public List<Offer> getMostLikedOffers(@RequestParam(required = true) String empId) {
		return (employeeService.topLikedOffersByEmployee(empId));
	}

	@GetMapping("/employees/{empId}")
	public ResponseEntity<Employee> viewEmployeeProfile(@PathVariable String empId) {
		if(empIdCache.contains(empId)) {
		Optional<Employee> viewProfile = employeeService.viewProfile(empId);
		if (viewProfile.isPresent()) {
			return ResponseEntity.ok(viewProfile.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		}
		else {
			throw new NoSuchEmpIdException("empid " + empId + " is invalid");
		}
	}

	@GetMapping("/employees")
	public List<Employee> viewAllEmployee() {
		return employeeRepository.findAll();
	}

}
