package vttp2023.batch3.ssf.frontcontroller.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(path="/protected")
public class ProtectedController 
{
	@GetMapping(path={"", "*"})
	public String checkAuthentication(HttpSession session)
	{
		if(session.getAttribute("authen")==null)
		{
			return "redirect:/";
		}
		return "protected/view1";
	}
	// TODO Task 5
	// Write a controller to protect resources rooted under /protected
}
