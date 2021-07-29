package com.ij026.team3.mfpe.employeemicroservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import com.ij026.team3.mfpe.employeemicroservice.dao.EmployeeRepository;
import com.ij026.team3.mfpe.employeemicroservice.feignclient.OfferMicroserviceFeign;
import com.ij026.team3.mfpe.employeemicroservice.feignclient.PointsMicroserviceFeign;
import com.ij026.team3.mfpe.employeemicroservice.model.Employee;
import com.ij026.team3.mfpe.employeemicroservice.model.Offer;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
class EmployeeServiceTest {
	@MockBean
	private OfferMicroserviceFeign offerClient;

	@MockBean
	private EmployeeRepository employeeRepository;

	@MockBean
	private PointsMicroserviceFeign pointsFeign;

	@Autowired
	@InjectMocks
	private EmployeeService employeeService;

	List<Offer> offers;

	Offer offer;

	String valid_jwt;

	String invalid_jwt;

	String valid_empId;

	String invalid_empId;

	Optional<Employee> findByEmpId;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		offer = new Offer();
		offer.setOfferId(1);
		offers = new ArrayList<Offer>();
		offers.add(offer);
		valid_jwt = "213425";
		invalid_jwt = "abcd";
		valid_empId = "guru";
		invalid_empId = "abcdx";
		Employee build = Employee.builder().empId(valid_empId).build();
		findByEmpId = Optional.of(build);

	}

	@AfterEach
	void tearDown() throws Exception {
		offer = null;
		offers = null;
		valid_empId = null;
		invalid_empId = null;
		valid_jwt = null;
		invalid_jwt = null;
		findByEmpId = null;
	}

	@Test
	void testOffersByEmployee() {
		// fail("Not yet implemented");
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		when(this.offerClient.getOfferDetailsByAuthor(valid_jwt, valid_empId)).thenReturn(ResponseEntity.ok(offers));
		when(this.pointsFeign.getPointsOfEmployee(valid_jwt, valid_empId)).thenReturn(ResponseEntity.ok(10));
		map.put("all-offers", offers);
		map.put("points-gained", 10);
		map.put("empId", valid_empId);
		assertEquals(map, this.employeeService.offersByEmployee(valid_jwt, valid_empId));

	}

	@Test
	void testOffersByEmployee_invalid_jwt() {
		when(this.offerClient.getOfferDetailsByAuthor(invalid_jwt, valid_empId))
				.thenReturn(ResponseEntity.status(400).build());
		when(this.pointsFeign.getPointsOfEmployee(invalid_jwt, valid_empId))
				.thenReturn(ResponseEntity.status(400).build());
		assertEquals(Map.of(), this.employeeService.offersByEmployee(invalid_jwt, valid_empId));
	}

	@Test
	void testOffersByEmployee_invalid_empId() {
		// Map<String, Object> map = new LinkedHashMap<String, Object>();
		when(this.offerClient.getOfferDetailsByAuthor(valid_jwt, invalid_empId))
				.thenReturn(ResponseEntity.status(400).build());
		when(this.pointsFeign.getPointsOfEmployee(valid_jwt, invalid_empId))
				.thenReturn(ResponseEntity.status(400).build());
		assertEquals(Map.of(), this.employeeService.offersByEmployee(valid_jwt, invalid_empId));

	}

	@Test
	void testTopLikedOffersByEmployee() {
		when(offerClient.getOfferDetailsByLikes(valid_jwt, 3, valid_empId)).thenReturn(ResponseEntity.ok(offers));

		assertEquals(offers, this.employeeService.topLikedOffersByEmployee(valid_jwt, valid_empId));

	}

	@Test
	void testTopLikedOffersByEmployee_invalid_jwt() {
		when(offerClient.getOfferDetailsByLikes(invalid_jwt, 3, valid_empId))
				.thenReturn(ResponseEntity.status(400).build());

		assertEquals(List.of(), this.employeeService.topLikedOffersByEmployee(invalid_jwt, valid_empId));

	}

	@Test
	void testTopLikedOffersByEmployee_invalid_empId() {
		when(offerClient.getOfferDetailsByLikes(valid_jwt, 3, invalid_empId))
				.thenReturn(ResponseEntity.status(400).build());

		assertEquals(List.of(), this.employeeService.topLikedOffersByEmployee(valid_jwt, invalid_empId));

	}

	@Test
	void testViewProfile() {
		// fail("Not yet implemented");
		when(employeeRepository.findByEmpId(valid_empId)).thenReturn(findByEmpId);
		when(pointsFeign.getPointsOfEmployee(valid_jwt, valid_empId)).thenReturn(ResponseEntity.ok(100));
		findByEmpId.get().setPointsGained(100);
		assertEquals(findByEmpId, employeeService.viewProfile(valid_jwt, valid_empId));

	}

}
