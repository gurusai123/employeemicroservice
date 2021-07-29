package com.ij026.team3.mfpe.employeemicroservice.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.ij026.team3.mfpe.employeemicroservice.model.Employee;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
class EmployeeRepositoryTest {
	@Mock
	private EmployeeRepository employeeRepository;
	private String valid_empId;
	private String invalid_empId;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		valid_empId = "guru";
		invalid_empId = "abcd";
	}

	@AfterEach
	void tearDown() throws Exception {
		valid_empId = null;
		invalid_empId = null;
	}

	@Test
	void test() {
		// fail("Not yet implemented");
		// employeeRepository.findByEmpId(empId);
		Employee emp = new Employee();
		emp.setEmpId(valid_empId);

		when(this.employeeRepository.findByEmpId(valid_empId)).thenReturn(Optional.of(emp));
		assertEquals(Optional.of(emp), this.employeeRepository.findByEmpId(valid_empId));
	}

}
