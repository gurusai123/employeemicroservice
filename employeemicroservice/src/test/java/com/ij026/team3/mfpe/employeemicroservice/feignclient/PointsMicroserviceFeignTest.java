package com.ij026.team3.mfpe.employeemicroservice.feignclient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
class PointsMicroserviceFeignTest {

	@Mock
	private PointsMicroserviceFeign pointsMicroserviceFeign;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetPointsOfEmployee() {
		when(pointsMicroserviceFeign.getPointsOfEmployee("valid_header", "guru")).thenReturn(ResponseEntity.ok(100));
		assertEquals(ResponseEntity.ok(100), pointsMicroserviceFeign.getPointsOfEmployee("valid_header", "guru"));
	}

}
