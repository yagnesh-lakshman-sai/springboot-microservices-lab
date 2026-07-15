package com.ums.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ums.dto.ProfileResponse;
import com.ums.service.LocationService;
import com.ums.service.UserService;


import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {

	@Autowired
	private UserService userService;

	@Autowired
	private LocationService locationService;

	@GetMapping("/dashboard")
	public String dashboard(HttpSession session, Model model) {

		String email = (String) session.getAttribute("userEmail");

		if (email == null) {
			return "redirect:/login";
		}

		ProfileResponse profile = userService.getProfile(email);

		model.addAttribute("profile", profile);

		return "dashboard";
	}

}