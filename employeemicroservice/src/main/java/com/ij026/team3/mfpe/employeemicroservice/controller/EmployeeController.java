package com.ij026.team3.mfpe.employeemicroservice.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ij026.team3.mfpe.employeemicroservice.model.Employee;
import com.ij026.team3.mfpe.employeemicroservice.model.Offer;
import com.ij026.team3.mfpe.employeemicroservice.service.EmployeeService;

@RestController
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/test")
	public String test() {
		return ("fdsgd");
	}

	@GetMapping("/employeeOffers/{empId}")
	public Map<String, Object> getEmployeeOffers(@PathVariable String empId) {
		return employeeService.offersByEmployee(empId);
	}
	
	@GetMapping("/offers/by-most-liked")
	public List<Offer> getMostLikedOffers(@RequestParam(required = true) String empId){
		return(employeeService.topLikedOffersByEmployee(empId));
	}
	
	@GetMapping("/employee")
	public Optional<Employee> viewEmployeeProfile(@RequestParam(required = true) String empId){
		return(employeeService.viewProfile(empId));
	}
	

}
