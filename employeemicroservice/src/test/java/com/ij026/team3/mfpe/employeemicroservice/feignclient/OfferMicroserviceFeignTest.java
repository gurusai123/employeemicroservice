package com.ij026.team3.mfpe.employeemicroservice.feignclient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.ResponseEntity;

import com.ij026.team3.mfpe.employeemicroservice.model.Offer;
import com.ij026.team3.mfpe.employeemicroservice.model.OfferCategory;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
class OfferMicroserviceFeignTest {

	@Mock
	private OfferMicroserviceFeign offerMicroserviceFeign;

	Offer offer = new Offer();

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
	void testGetOffers() {
		when(offerMicroserviceFeign.getOffers("valid_jwt")).thenReturn(ResponseEntity.ok(List.of(offer)));
		assertEquals(ResponseEntity.ok(List.of(offer)), this.offerMicroserviceFeign.getOffers("valid_jwt"));
	}

	@Test
	void testGetOfferDetails() {
		when(offerMicroserviceFeign.getOfferDetails("valid_token", "valid_offerid"))
				.thenReturn(ResponseEntity.ok(offer));
		assertEquals(ResponseEntity.ok(offer),
				this.offerMicroserviceFeign.getOfferDetails("valid_token", "valid_offerid"));

	}

	@Test
	void testGetOfferDetailsByCategory() {
		when(offerMicroserviceFeign.getOfferDetailsByCategory("valid_token", OfferCategory.COMPUTER_ACCESORIES))
				.thenReturn(ResponseEntity.ok(List.of(offer)));
		assertEquals(ResponseEntity.ok(List.of(offer)), this.offerMicroserviceFeign
				.getOfferDetailsByCategory("valid_token", OfferCategory.COMPUTER_ACCESORIES));

	}

	@Test
	void testGetOfferDetailsByLikes() {
		when(offerMicroserviceFeign.getOfferDetailsByLikes("valid_jwt", 1, "guru"))
				.thenReturn(ResponseEntity.ok(List.of(offer)));
		assertEquals(ResponseEntity.ok(List.of(offer)),
				this.offerMicroserviceFeign.getOfferDetailsByLikes("valid_jwt", 1, "guru"));
	}

	@Test
	void testGetOfferDetailsByPostDate() {
		when(offerMicroserviceFeign.getOfferDetailsByPostDate("valid_jwt", "29/7/2021"))
				.thenReturn(ResponseEntity.ok(List.of(offer)));
		assertEquals(ResponseEntity.ok(List.of(offer)),
				offerMicroserviceFeign.getOfferDetailsByPostDate("valid_jwt", "29/7/2021"));
	}

	@Test
	void testGetOfferDetailsByAuthor() {
		when(offerMicroserviceFeign.getOfferDetailsByAuthor("valid_jwt", "guru"))
				.thenReturn(ResponseEntity.ok(List.of(offer)));
		assertEquals(ResponseEntity.ok(List.of(offer)),
				offerMicroserviceFeign.getOfferDetailsByAuthor("valid_jwt", "guru"));
	}

	@Test
	void testAddOffer() {
		when(offerMicroserviceFeign.addOffer("valid_jwt", offer)).thenReturn(ResponseEntity.ok(true));
		assertEquals(ResponseEntity.ok(true), offerMicroserviceFeign.addOffer("valid_jwt", offer));
	}

	@Test
	void testLikeOffer() {
		when(offerMicroserviceFeign.likeOffer("valid_jwt", 0, "guru")).thenReturn(ResponseEntity.ok(offer));
		assertEquals(ResponseEntity.ok(offer), offerMicroserviceFeign.likeOffer("valid_jwt", 0, "guru"));
	}

}
