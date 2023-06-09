package vttp2023.batch3.ssf.frontcontroller.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp2023.batch3.ssf.frontcontroller.services.AuthenticationService;

@Controller
@RequestMapping(path="/protected")
public class ProtectedController 
{
	@Autowired
	private AuthenticationService userService;

	//@PostMapping (path="/")
	
	// TODO Task 5
	// Write a controller to protect resources rooted under /protected
}
