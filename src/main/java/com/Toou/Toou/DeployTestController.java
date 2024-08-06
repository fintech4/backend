package com.Toou.Toou;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeployTestController {
	@GetMapping("/")
	public String init(){
		return "Server Started!!";
	}
}
