package com.ij026.team3.mfpe.employeemicroservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient(url = "http://localhost:5555/points-service/")
public interface PointsMicroserviceFeign {
	//uri:TODO
	Integer getPointsOfEmployee(@RequestParam(required = true)String empId);
	//uri:TODO
	boolean refreshPointsOfEmployee(@RequestParam(required = true)String empId);
}
