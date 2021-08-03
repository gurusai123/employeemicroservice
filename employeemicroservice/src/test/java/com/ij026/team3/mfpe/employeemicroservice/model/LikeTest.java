package com.ij026.team3.mfpe.employeemicroservice.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
class LikeTest {

	@Test
	void testNoArgs() {
		assertThat(new Like()).isNotNull();
	}
	@Test
	void testAllArgs() {
		Like like = new Like("nikky", LocalDate.now());
		assertThat(assertThat(like).isNotNull());
	}


}
