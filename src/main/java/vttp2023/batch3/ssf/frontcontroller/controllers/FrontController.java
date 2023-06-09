package vttp2023.batch3.ssf.frontcontroller.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vttp2023.batch3.ssf.frontcontroller.model.User;
import vttp2023.batch3.ssf.frontcontroller.services.AuthenticationService;

@Controller
@RequestMapping (path="/")
public class FrontController 
{
	@Autowired
	private AuthenticationService userService;

	@GetMapping (path="/")
	public String landingPage(Model m, HttpSession sess)
	{	
		sess.invalidate();
		m.addAttribute("User", new User());
		return "view0";
	}
	@GetMapping (path="/retry")
	public String Page(Model m, HttpSession sess)
	{	
		m.addAttribute("User", new User());
		return "view0";
	}

	@PostMapping (path="/login", consumes ="application/x-www-form-urlencoded")
	public String loginPage(@Valid User user, BindingResult bind, 
					HttpSession session, Model model) throws Exception
	{
		if(bind.hasErrors())
		{
			return "view0";
		}

		if(!session.getAttribute("answer").equals(user.getReply()))
		{
			user.setCount(user.getCount()+1);
			if(user.getCount()>=3)
			{
				if(user.getDisabled())
				{
					//check if time has passed
					if(userService.timeCheck(user.getName()))
					{
						user.setDisabled(false);
					}
					return "view0";
				}
				userService.disableUser(user.getName());
				model.addAttribute("User", user.getName());
				return "view2";
			}
			user.setAuthenticated(false);
			session.setAttribute("count", user.getCount());

			int x = userService.randomNum();
			int y = userService.randomNum();
			String xString = Integer.toString(x);
			String yString = Integer.toString(y);
			String opeartion = userService.randomOpe();
			float xy = userService.calculate(x, y, opeartion);
			String expression = xString+opeartion+yString;
			String xyString = Float.toString(xy);

			session.setAttribute("answer", xyString);
			model.addAttribute("captcha", expression);
			return "view0";
		}
	
		Boolean isAuthen = userService.authenticate(user.getName(), user.getPassword());
		
		if(isAuthen==true)
		{
			user.setAuthenticated(true);
			return "view1";
		} else
		{	
			user.setCount(user.getCount()+1);
			if(user.getCount()==3)
			{
				userService.disableUser(user.getName());
				user.setDisabled(true);
				model.addAttribute("User", user.getName());
				return "view2";
			}
			user.setAuthenticated(false);
			session.setAttribute("count", user.getCount());

			int x = userService.randomNum();
			int y = userService.randomNum();
			String xString = Integer.toString(x);
			String yString = Integer.toString(y);
			String opeartion = userService.randomOpe();
			float xy = userService.calculate(x, y, opeartion);
			String expression = "What is "+xString+opeartion+yString+"?";
			String xyString = Float.toString(xy);

			session.setAttribute("answer", xyString);
			model.addAttribute("captcha", expression);
		}
		return "view0";
	}


	// TODO: Task 2, Task 3, Task 4, Task 6
	

}
