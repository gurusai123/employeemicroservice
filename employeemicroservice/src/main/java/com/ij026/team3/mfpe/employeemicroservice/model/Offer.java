package com.ij026.team3.mfpe.employeemicroservice.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Offer {
	private Integer offerId;
	private LocalDate createdAt;
	private LocalDate closedAt;
	private String details;
	private Integer likes;
	private OfferCategory offerCategory;
	private boolean isOpen;
	private String buyerId; // -> empId

}