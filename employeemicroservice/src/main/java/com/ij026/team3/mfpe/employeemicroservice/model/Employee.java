package com.ij026.team3.mfpe.employeemicroservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
public class Employee {
	@Id
	private String empId;
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@NotNull
	@Pattern(regexp = "[a-zA-Z0-9]{8}", message = "only aphanumerics with eight charecters")
	private String password;
	@Email(message = "Email should not be empty")
	@Column(unique = true, updatable = false)
	@NotNull
	private String email;
	@NotNull
	@Pattern(regexp = "^[6789][0-9]{9}", message = "phone number should be of ten digits")
	private String phoneNumber;
	@Min(value = 0, message = "points can not be negitive")
	private Integer pointsGained;
}
