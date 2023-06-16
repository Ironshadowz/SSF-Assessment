package vttp2023.batch3.ssf.frontcontroller.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vttp2023.batch3.ssf.frontcontroller.model.User;
import vttp2023.batch3.ssf.frontcontroller.services.AuthenticationService;

@Controller
@RequestMapping
public class FrontController 
{
	@Autowired
	private AuthenticationService userService;

	@GetMapping(path = "/")
	public String landingPage(Model m, HttpSession session, User user)
	{	
		m.addAttribute("User", new User());
		return "view0";
	}
	
	@PostMapping (path="/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String loginPage(@Valid @ModelAttribute User user, BindingResult bind, 
					HttpSession session, @RequestParam(required=false) String reply, Model model) 
	{
		//model.addAttribute("User", new User());
		if(bind.hasErrors())
		{
			System.out.println("bind");
			return "view0";
		}
		if(userService.isLocked(user.getUsername(), session))
		{
			model.addAttribute("User", user);
			return "view2";
		}
		if(session.getAttribute("answer")!=null)
		{	//Check answer
			System.out.println("Captcha answers "+reply+" "+session.getAttribute("answer"));
			boolean checkAnswer = userService.checkAnswer(session, reply);
			//If answer is incorrect
			if(checkAnswer==false)
			{
				if(session.getAttribute(user.getUsername())==null)
				{
					int first = 1;
					System.out.println(user.getUsername()+" count 1");
					session.setAttribute(user.getUsername(), first);
					String captcha = userService.generateCaptcha(session);
					System.out.println(captcha);
					model.addAttribute("captcha", captcha);
					return "view0";
				} else
				{	int count = (int) session.getAttribute(user.getUsername());
					int newCount = count+1;
					System.out.println(user.getUsername()+" count "+newCount);
					session.setAttribute(user.getUsername(), newCount);
					String captcha = userService.generateCaptcha(session);
					System.out.println(captcha);
					model.addAttribute("captcha", captcha);
				}
				if((int)session.getAttribute(user.getUsername())>=3)
				{
					System.out.println("Locking "+user.getUsername());
					userService.disableUser(user.getUsername());
					model.addAttribute("User", user);
					return "view2";
				}
				return "view0";
			}
		}
		try
		{
			System.out.println("authen");
			userService.authenticate(user.getUsername(), user.getPassword());
		} catch(Exception ex)
		{
			//create captcha and add to model
			System.out.println("captcha");
			if(session.getAttribute(user.getUsername())==null)
			{
				int i = 1;
				System.out.println(user.getUsername()+" count 1");
				session.setAttribute(user.getUsername(), i);

			} else
			{
				int count = (int) session.getAttribute(user.getUsername());
				int newCount = count+1;
				System.out.println(user.getUsername()+" count "+newCount);
				session.setAttribute(user.getUsername(), newCount);
			}
			if((int)session.getAttribute(user.getUsername())>=3)
			{
				System.out.println("Locking "+user.getUsername());
				userService.disableUser(user.getUsername());
				model.addAttribute("User", user);
				return "view2";
			}
			String captcha = userService.generateCaptcha(session);
			System.out.println(captcha);
			model.addAttribute("captcha", captcha);
			return "view0";
		}

		session.removeAttribute("answer");
		session.setAttribute("authen", true);
		return "redirect:/protected";
	}


	// TODO: Task 2, Task 3, Task 4, Task 6
	

}
