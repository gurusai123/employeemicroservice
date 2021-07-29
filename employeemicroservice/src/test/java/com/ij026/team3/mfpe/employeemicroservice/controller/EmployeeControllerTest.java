package com.ij026.team3.mfpe.employeemicroservice.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ij026.team3.mfpe.employeemicroservice.dao.EmployeeRepository;
import com.ij026.team3.mfpe.employeemicroservice.feignclient.AuthFeign;
import com.ij026.team3.mfpe.employeemicroservice.model.Employee;
import com.ij026.team3.mfpe.employeemicroservice.service.EmployeeService;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
class EmployeeControllerTest {
	@MockBean
	private EmployeeRepository employeeRepository;

	@MockBean
	private EmployeeService employeeService;

	@MockBean
	private AuthFeign authFeign;

	@InjectMocks
	@Autowired
	private EmployeeController controller;

	String valid_jwt;

	String invalid_jwt;

	String valid_empId;

	String invalid_empId;

	Optional<Employee> viewProfile;

	Optional<Employee> invalid_viewProfile = null;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		valid_jwt = "213425";
		invalid_jwt = "abcd";
		valid_empId = "guru";
		invalid_empId = "abcdx";
		Employee build = Employee.builder().empId(valid_empId).build();
		viewProfile = Optional.of(build);
		Employee build1 = Employee.builder().empId(null).build();
		invalid_viewProfile = Optional.of(build1);
	}

	@AfterEach
	void tearDown() throws Exception {
		valid_empId = null;
		invalid_empId = null;
		valid_jwt = null;
		invalid_jwt = null;
	}

	@Test
	void testGetEmployeeOffers() {
		when(this.authFeign.authorizeToken(valid_jwt)).thenReturn(ResponseEntity.ok(this.valid_empId));
		Object obj = new Object();
		when(this.employeeService.offersByEmployee(valid_jwt, valid_empId)).thenReturn(Map.of("test", obj));
		ResponseEntity<Map<String, Object>> ex = ResponseEntity.ok(Map.of("test", obj));
		ResponseEntity<Map<String, Object>> actual = this.controller.getEmployeeOffers(valid_jwt, valid_empId);
		assertEquals(ex, actual);

	}

	@Test
	void testGetEmployeeOffers_invalid_jwt() {
		when(this.authFeign.authorizeToken(invalid_jwt))
				.thenReturn(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
		ResponseEntity<Map<String, Object>> ex = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		ResponseEntity<Map<String, Object>> actual = this.controller.getEmployeeOffers(invalid_jwt, valid_empId);
		assertEquals(ex, actual);

	}

	@Test
	void testGetEmployeeOffers_invalid_empId() {
		when(this.authFeign.authorizeToken(valid_jwt)).thenReturn(ResponseEntity.ok(this.valid_empId));
		when(this.employeeService.offersByEmployee(valid_jwt, invalid_empId)).thenReturn(Map.of());
		ResponseEntity<Map<String, Object>> ex = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		ResponseEntity<Map<String, Object>> actual = this.controller.getEmployeeOffers(valid_jwt, invalid_empId);
		assertEquals(ex, actual);

	}

	@Test
	void testGetMostLikedOffers() {
		when(this.authFeign.authorizeToken(valid_jwt)).thenReturn(ResponseEntity.ok(this.valid_empId));
		Object obj = new Object();
		when(this.employeeService.offersByEmployee(valid_jwt, valid_empId)).thenReturn(Map.of("test", obj));
		ResponseEntity<Map<String, Object>> ex = ResponseEntity.ok(Map.of("test", obj));
		ResponseEntity<Map<String, Object>> actual = this.controller.getMostLikedOffers(valid_jwt, valid_empId);
		assertEquals(ex, actual);
	}

	@Test
	void testGetMostLikedOffers_invalid_jwt() {
		when(this.authFeign.authorizeToken(invalid_jwt))
				.thenReturn(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
		ResponseEntity<Map<String, Object>> ex = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		ResponseEntity<Map<String, Object>> actual = this.controller.getMostLikedOffers(invalid_jwt, valid_empId);
		assertEquals(ex, actual);

	}

	@Test
	void testGetMostLikedOffers_invalid_empId() {
		when(this.authFeign.authorizeToken(valid_jwt)).thenReturn(ResponseEntity.ok(this.valid_empId));
		when(this.employeeService.offersByEmployee(valid_jwt, invalid_empId)).thenReturn(Map.of());
		ResponseEntity<Map<String, Object>> ex = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		ResponseEntity<Map<String, Object>> actual = this.controller.getMostLikedOffers(valid_jwt, invalid_empId);
		assertEquals(ex, actual);

	}

	@Test
	void testViewEmployeeProfile() {
		when(this.authFeign.authorizeToken(valid_jwt)).thenReturn(ResponseEntity.ok(valid_empId));
		when(employeeService.viewProfile(valid_jwt, valid_empId)).thenReturn(viewProfile);
		assertEquals(ResponseEntity.ok(viewProfile.get()), controller.viewEmployeeProfile(valid_jwt, valid_empId));

	}

	@Test
	void testViewEmployeeProfile_invalid_jwt() {
		when(this.authFeign.authorizeToken(invalid_jwt))
				.thenReturn(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
		assertEquals(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(),
				this.controller.viewEmployeeProfile(invalid_jwt, valid_empId));

	}

	@Test
	void testViewEmployeeProfile_invalid_empId() {
		when(this.authFeign.authorizeToken(valid_jwt)).thenReturn(ResponseEntity.ok(valid_empId));
		assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST).build(),
				this.controller.viewEmployeeProfile(valid_jwt, invalid_empId));
	}

	/*
	 * void testViewEmployeeProfile_notPresent_viewProfile() {
	 * when(this.authFeign.authorizeToken(valid_jwt)).thenReturn(ResponseEntity.ok(
	 * valid_empId)); }
	 */

	@Test
	void testViewAllEmployee() {
		when(this.authFeign.authorizeToken(valid_jwt)).thenReturn(ResponseEntity.ok(valid_empId));
		Employee emp = new Employee();
		emp.setEmpId(valid_empId);
		when(employeeRepository.findAll()).thenReturn(List.of(emp));
		ResponseEntity<List<Employee>> all = ResponseEntity.ok(employeeRepository.findAll());
		assertEquals(all, this.controller.viewAllEmployee(valid_jwt));
	}
	@Test
	void testViewEmployee_invalid_jwt() {
		when(this.authFeign.authorizeToken(invalid_jwt))
				.thenReturn(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
		assertEquals(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(),
				this.controller.viewAllEmployee(invalid_jwt));

	}

}
