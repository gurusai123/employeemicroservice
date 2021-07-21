package com.ij026.team3.mfpe.employeemicroservice.feignclient;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.ij026.team3.mfpe.employeemicroservice.model.Offer;
import com.ij026.team3.mfpe.employeemicroservice.model.OfferCategory;

@FeignClient(name = "offers",url = "http://localhost:8080/offer-service/")
public interface OfferMicroserviceFeign {
	@GetMapping("/test")
	public String test(@RequestParam(required = false) Map<String, Object> map);

	@GetMapping("/offers")
	public Collection<Offer> getOffers();

	@GetMapping("/offers/{offerId}")
	public ResponseEntity<?> getOfferDetails(@PathVariable String offerId);

	@GetMapping("/offers/search/by-category")
	public ResponseEntity<?> getOfferDetailsByCategory(@RequestParam(required = true) OfferCategory offerCategory);

	@GetMapping("/offers/search/by-likes")
	public ResponseEntity<?> getOfferDetailsByLikes(@RequestParam(required = false, defaultValue = "3") Integer limit,
			@RequestParam(required = false) String empId);

	@GetMapping("/offers/search/by-creation-date")
	public ResponseEntity<?> getOfferDetailsByPostDate(@RequestParam(required = true) String createdOn);

	@GetMapping("/offers/search/by-author")
	public ResponseEntity<?> getOfferDetailsByAuthor(@RequestParam(required = true) String authorId,
			@RequestParam(required = false) boolean open);

	@PostMapping("/offers")
	public ResponseEntity<Object> addOffer(@Valid @RequestBody Offer newOffer);
}
