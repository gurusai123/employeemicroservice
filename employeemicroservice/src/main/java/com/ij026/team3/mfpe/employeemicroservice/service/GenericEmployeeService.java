package com.ij026.team3.mfpe.employeemicroservice.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ij026.team3.mfpe.employeemicroservice.model.Employee;
import com.ij026.team3.mfpe.employeemicroservice.model.Offer;

/**
 * @author <b>guru sai</b> Functional Requirements Employee Microservice allows
 *         to view the offers posted by an employee and view the most liked
 *         offers by an Employee. This allows the portal to display the details
 *         whenever they have logged in.
 * 
 *         To view the following: Offers posted by an employee Offers most liked
 *         by an employee Own Profile
 * 
 */
public interface GenericEmployeeService {
	public Map<String, Object> offersByEmployee(String empId);

	public List<Offer> topLikedOffersByEmployee(String empId);

	public Optional<Employee> viewProfile(String empId);

}