package com.ij026.team3.mfpe.employeemicroservice.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ij026.team3.mfpe.employeemicroservice.dao.EmployeeRepository;
import com.ij026.team3.mfpe.employeemicroservice.feignclient.OfferMicroserviceFeign;
import com.ij026.team3.mfpe.employeemicroservice.feignclient.PointsMicroserviceFeign;
import com.ij026.team3.mfpe.employeemicroservice.model.Employee;
import com.ij026.team3.mfpe.employeemicroservice.model.Offer;

@Service
public class EmployeeService implements GenericEmployeeService {
	@Autowired
	private OfferMicroserviceFeign offerClient;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private PointsMicroserviceFeign pointsFeign;

	private boolean isSuccessful(ResponseEntity<?> response) {
		return response.getStatusCodeValue() >= 200 && response.getStatusCodeValue() < 300;
	}

	@Override
	public Map<String, Object> offersByEmployee(String empId) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		ResponseEntity<?> offerDetails = offerClient.getOfferDetailsByAuthor(empId, true);
		Integer points = pointsFeign.getPointsOfEmployee(empId).getBody();
		if (isSuccessful(offerDetails)) {
			@SuppressWarnings("unchecked")
			List<Offer> offer = (List<Offer>) offerDetails.getBody();
			map.put("all-offers", offer);
			map.put("points-gained", points);
			map.put("empId", empId);
			return (map);
		}
		return Map.of();
	}

	@Override
	public List<Offer> topLikedOffersByEmployee(String empId) {
		ResponseEntity<?> offerDetails = offerClient.getOfferDetailsByLikes(3, empId);
		if (isSuccessful(offerDetails)) {
			@SuppressWarnings("unchecked")
			List<Offer> offer = (List<Offer>) offerDetails.getBody();
			return (offer);
		}
		return List.of();
	}

	@Override
	public Optional<Employee> viewProfile(String empId) {
		Optional<Employee> findByEmpId = employeeRepository.findByEmpId(empId);
		ResponseEntity<Integer> pointsOfEmployee = pointsFeign.getPointsOfEmployee(empId);
		int points = 0;

		if (pointsOfEmployee.getStatusCodeValue() >= 200 && pointsOfEmployee.getStatusCodeValue() < 300) {
			points = pointsOfEmployee.getBody();
		}

		if (findByEmpId.isPresent()) {
			findByEmpId.get().setPointsGained(points);
			employeeRepository.save(findByEmpId.get());
		}
		return (findByEmpId);
	}

}
