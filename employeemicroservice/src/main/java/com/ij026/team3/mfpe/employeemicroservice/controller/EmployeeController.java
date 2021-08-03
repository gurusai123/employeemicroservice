package com.ij026.team3.mfpe.employeemicroservice.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.ij026.team3.mfpe.employeemicroservice.dao.EmployeeRepository;
import com.ij026.team3.mfpe.employeemicroservice.feignclient.AuthFeign;
import com.ij026.team3.mfpe.employeemicroservice.model.Employee;
import com.ij026.team3.mfpe.employeemicroservice.service.EmployeeService;

import lombok.extern.log4j.Log4j2;

@RestController
@CrossOrigin
@Log4j2
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

	@GetMapping("/api/ifexists/employee/{empId}")
	public ResponseEntity<Boolean> checkEmpID(@RequestHeader(name = "Authorization") String jwtToken,
			@PathVariable String empId) {
		log.debug("api call from remote service for empId check");
		if (isAuthorized(jwtToken)) {
			return ResponseEntity.ok(ifEmployeeExists(empId));
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	public EmployeeController() {
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

	@GetMapping("/employees/{empId}/offers")
	public ResponseEntity<Map<String, Object>> getEmployeeOffers(@RequestHeader(name = "Authorization") String jwtToken,
			@PathVariable String empId) {
		if (isAuthorized(jwtToken)) {
			if (ifEmployeeExists(empId)) {
				return ResponseEntity.ok(employeeService.offersByEmployee(jwtToken, empId));
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	private boolean ifEmployeeExists(String empId) {
		return employeeRepository.existsById(empId);
	}

	@GetMapping("/employees/{empId}/offers/by-most-liked")
	public ResponseEntity<Map<String, Object>> getMostLikedOffers(
			@RequestHeader(name = "Authorization") String jwtToken, @PathVariable String empId) {
		if (isAuthorized(jwtToken)) {
			if (ifEmployeeExists(empId)) {
				return ResponseEntity.ok(employeeService.offersByEmployee(jwtToken, empId));
			} else {
				System.err.println("empId");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@GetMapping("/employees/{empId}")
	public ResponseEntity<Employee> viewEmployeeProfile(@RequestHeader(name = "Authorization") String jwtToken,
			@PathVariable String empId) {
		if (isAuthorized(jwtToken)) {
			if (ifEmployeeExists(empId)) {
				Optional<Employee> viewProfile = employeeService.viewProfile(jwtToken, empId);
				if (viewProfile.isPresent()) {
					return ResponseEntity.ok(viewProfile.get());
				} else {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
				}
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> viewAllEmployee(@RequestHeader(name = "Authorization") String jwtToken) {
		if (isAuthorized(jwtToken))
			return ResponseEntity.ok(employeeRepository.findAll());
		else
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> handleAllException(RuntimeException run_exception) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(run_exception.getMessage());
	}
}
