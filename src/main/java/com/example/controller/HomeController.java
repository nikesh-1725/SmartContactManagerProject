package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.entity.Contact;
import com.example.entity.User;
import com.example.helper.Message;
import com.example.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping("/")
	public String home(Model model)
	{
		model.addAttribute("title","Home-Smart Contact Manager");
		return "home";
	}
	
	@GetMapping("/test")
	@ResponseBody
	public String test()
	{
		User user=new User();
		user.setName("Nikesh Nikes");
		user.setEmail("nikesh@gmail.com");
		
		Contact contact=new Contact();
		user.getContacts().add(contact);
		
		userRepository.save(user);
		
		return "Hello its Working";
		}
	
	@RequestMapping("/signUp")
	public String signUp(Model model)
	{
		model.addAttribute("title","Register-Smart Contact Manager");
		model.addAttribute("user", new User());
		return "signUp";
	}
	
	// this handler for register the User
	
	@RequestMapping(value="/do_register", method=RequestMethod.POST)
	public String registerUser(@ModelAttribute("user") User user, @RequestParam(value="agreement", defaultValue="false") boolean agreement, Model model, HttpSession session)
	{
		
		try {
			if (!agreement) {
				System.out.println("You have not accepted agreement");
				throw new Exception("You have not accepted agreement");
				
			}
			user.setRole("Role_User");
			user.setEnabled(true);
			user.setImageUrl("default.png");
			System.out.println("Agreement: " + agreement);
			System.out.println("User: " + user);
			User result = userRepository.save(user);
			model.addAttribute("user", new User());
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("Sucessfully Registered !!", "alert-sucess"));
			return "signUp";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("Something went wrong!!"+e.getMessage(), "alert-danger"));
			
			 return "signUp";
		} 
		
		
		
	   
	}

}
