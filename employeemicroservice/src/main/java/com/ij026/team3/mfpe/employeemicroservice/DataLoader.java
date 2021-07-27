package com.ij026.team3.mfpe.employeemicroservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.ij026.team3.mfpe.employeemicroservice.dao.EmployeeRepository;
import com.ij026.team3.mfpe.employeemicroservice.model.Employee;

@Component
public class DataLoader implements ApplicationRunner {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("data inserted");
		employeeRepository.save(Employee.builder().empId("guru").firstName("C. Guru").lastName("Sai")
				.email("abc@gmail.com").password("abcd1234").pointsGained(0).phoneNumber("9076003210").build());
		employeeRepository.save(Employee.builder().empId("nikky").firstName("Niky").lastName("Gupta")
				.email("bcd@gmail.com").password("abcd1234").pointsGained(0).phoneNumber("9800110210").build());
		employeeRepository.save(Employee.builder().empId("subsa").firstName("Subham").lastName("Santra")
				.email("cde@gmail.com").password("abcd1234").pointsGained(0).phoneNumber("9876540219").build());
		employeeRepository.save(Employee.builder().empId("rish").firstName("Rhishabh").lastName("Kothari")
				.email("def@gmail.com").password("abcd1234").pointsGained(0).phoneNumber("9999540219").build());
		employeeRepository.save(Employee.builder().empId("ujjw").firstName("Ujjwal").lastName("Srivastava")
				.email("efg@gmail.com").password("abcd1234").pointsGained(0).phoneNumber("7777540219").build());
	}

}