package com.xyz.controller;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import com.xyz.Response.ApiResponse;



@RestController
public class HomeController {
	
	@GetMapping("/")
	public ApiResponse home() {
		return new ApiResponse("welcome to ecommerce multivendor project");
	}

}
