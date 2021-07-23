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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ij026.team3.mfpe.employeemicroservice.dao.EmployeeRepository;
import com.ij026.team3.mfpe.employeemicroservice.exception.NoSuchEmpIdException;
import com.ij026.team3.mfpe.employeemicroservice.feignclient.AuthFeign;
import com.ij026.team3.mfpe.employeemicroservice.model.Employee;
import com.ij026.team3.mfpe.employeemicroservice.model.Offer;
import com.ij026.team3.mfpe.employeemicroservice.service.EmployeeService;

@RestController
public class EmployeeController {
	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private AuthFeign authFeign;

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

	private boolean isAuthorized(String jwtToken) {
		try {
			ResponseEntity<String> authorizeToken = authFeign.authorizeToken(jwtToken);
			boolean ok = (authorizeToken.getStatusCodeValue() == 200);
			if (ok) {
				System.err.println("Authorized");
			} else {
				System.err.println("Not Authorized");
			}
			return ok;
		} catch (Exception e) {
			System.err.println("Connection failure");
			return false;
		}
	}

	@GetMapping("/employeeOffers/{empId}")
	public ResponseEntity<Map<String, Object>> getEmployeeOffers(@RequestHeader(name = "Authorization") String jwtToken,
			@PathVariable String empId) {
		if (isAuthorized(jwtToken)) {
			if (empIdCache.contains(empId)) {
				return ResponseEntity.ok(employeeService.offersByEmployee(jwtToken,empId));
			} else {
				return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("/offers/by-most-liked")
	public List<Offer> getMostLikedOffers(@RequestHeader(name = "Authorization") String jwtToken,
			@RequestParam(required = true) String empId) {
		if (isAuthorized(jwtToken)) {
			if (empIdCache.contains(empId)) {
				return (List<Offer>) (employeeService.offersByEmployee(jwtToken,empId));
			} else {
				return (List<Offer>) ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		} else {
			return (List<Offer>) ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("/employees/{empId}")
	public ResponseEntity<Employee> viewEmployeeProfile(@RequestHeader(name = "Authorization") String jwtToken,
			@PathVariable String empId) {
		if (isAuthorized(jwtToken)) {
			if (empIdCache.contains(empId)) {
				Optional<Employee> viewProfile = employeeService.viewProfile(empId);
				if (viewProfile.isPresent()) {
					return ResponseEntity.ok(viewProfile.get());
				} else {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
				}
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("/employees")
	public List<Employee> viewAllEmployee(@RequestHeader(name = "Authorization") String jwtToken) {
		if (isAuthorized(jwtToken))
			return employeeRepository.findAll();
		else
			return (List<Employee>) ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

}
