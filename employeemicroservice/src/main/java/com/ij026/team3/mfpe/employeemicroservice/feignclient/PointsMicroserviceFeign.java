package com.ij026.team3.mfpe.employeemicroservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="points", url = "http://localhost:5555/points-service/")
public interface PointsMicroserviceFeign {
	// uri:TODO
	@GetMapping("/getPointsOfEmployee/{empId}")
	ResponseEntity<Integer> getPointsOfEmployee(@PathVariable String empId);

//	// uri:TODO
//	@GetMapping("/test_2")
//	boolean refreshPointsOfEmployee(@RequestParam(required = true) String empId);
}
