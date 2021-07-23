package com.ij026.team3.mfpe.employeemicroservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "points", url = "${PMS_URL:http://localhost:5555/points-service}")
public interface PointsMicroserviceFeign {
	
	@GetMapping("/getPointsOfEmployee/{empId}")
	ResponseEntity<Integer> getPointsOfEmployee(@RequestHeader(name = "Authorization") String authHeader, @PathVariable String empId);
}
