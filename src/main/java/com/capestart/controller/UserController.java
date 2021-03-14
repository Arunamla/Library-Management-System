package com.capestart.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.capestart.entity.Role;
import com.capestart.entity.User;
import com.capestart.service.BookService;
import com.capestart.service.UserDetailsServiceImpl;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
 	@RequestMapping("/showAll")
	public String listOfUser(Model model) {
		List<User> listUser = userDetailsService.listAll();
		model.addAttribute("listUser", listUser);
		
		return "userList";
	}
	
	@RequestMapping("/new")
	public String showNewUser(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		
		List<String> listRole = new ArrayList<>();
		listRole.add("ROLE_ADMIN");
		listRole.add("ROLE_USER");
		
		model.addAttribute("listRole", listRole);
		
		return "new_user";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("user") User user) {
		userDetailsService.save(user);
		
		return "redirect:/user/showAll";
	}
	
	@RequestMapping("/edit/{id}")
	public ModelAndView showEditUserForm(@PathVariable(name = "id") Long id) {
		ModelAndView mav = new ModelAndView("edit_user");
		
		User user = userDetailsService.get(id);
		user.setPassword("");
		
		mav.addObject("user", user);
		
		List<String> listRole = new ArrayList<>();

		
		listRole.add("ROLE_ADMIN");
		listRole.add("ROLE_USER");
		mav.addObject("listRole",listRole);
		
		return mav;
	}	
	
	@RequestMapping("/delete/{id}")
	public String deleteUser(@PathVariable(name = "id") Long id) {
		
		bookService.updateBookByLentUserId(id);
		userDetailsService.delete(id);
		
		return "redirect:/user/showAll";
	}
}
