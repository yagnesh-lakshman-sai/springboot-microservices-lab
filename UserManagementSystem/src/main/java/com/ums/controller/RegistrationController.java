package com.ums.controller;

import org.springframework.validation.BindingResult;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ums.dto.CreatePasswordRequest;
import com.ums.dto.ForgotPasswordRequest;
import com.ums.dto.LoginRequest;
import com.ums.dto.RegistrationRequest;
import com.ums.dto.ResetPasswordRequest;
import com.ums.dto.UpdateProfileRequest;
import com.ums.entity.CityEntity;
import com.ums.entity.StateEntity;
import com.ums.service.LocationService;
import com.ums.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class RegistrationController {

	@Autowired
	private UserService userService;

	@Autowired
	private LocationService locationService;

	@GetMapping("/")
	public String home() {
		return "redirect:/register";
	}

	@GetMapping("/register")
	public String loadRegisterPage(Model model) {

		model.addAttribute("registrationRequest", new RegistrationRequest());

		model.addAttribute("countries", locationService.getAllCountries());

		return "register";
	}

	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute("registrationRequest") RegistrationRequest request,
			BindingResult result, Model model) {

		if (result.hasErrors()) {

			model.addAttribute("countries", locationService.getAllCountries());

			return "register";
		}

		String message = userService.registerUser(request);

		model.addAttribute("msg", message);

		model.addAttribute("registrationRequest", new RegistrationRequest());

		model.addAttribute("countries", locationService.getAllCountries());

		return "register";

	}

	@ResponseBody
	@GetMapping("/states/{countryId}")
	public List<StateEntity> getStates(@PathVariable Integer countryId) {

		return locationService.getStates(countryId);

	}

	@ResponseBody
	@GetMapping("/cities/{stateId}")
	public List<CityEntity> getCities(@PathVariable Integer stateId) {

		return locationService.getCities(stateId);

	}

	@ResponseBody
	@GetMapping("/check-email")
	public boolean checkEmail(@RequestParam String email) {

		return userService.checkEmail(email);

	}

	@GetMapping("/verify")
	public String verifyAccount(@RequestParam String token, Model model) {

		CreatePasswordRequest request = new CreatePasswordRequest();

		request.setToken(token);

		model.addAttribute("createPasswordRequest", request);

		return "create-password";
	}

	@PostMapping("/create-password")
	public String createPassword(

			@Valid @ModelAttribute("createPasswordRequest") CreatePasswordRequest request,

			BindingResult result,

			Model model) {

		if (result.hasErrors()) {
			return "create-password";
		}

		String message = userService.createPassword(request);

		model.addAttribute("msg", message);

		model.addAttribute("loginRequest", new LoginRequest());

		return "login";
	}

	@GetMapping("/login")
	public String loadLogin(Model model) {

		model.addAttribute("loginRequest", new LoginRequest());

		return "login";
	}

	@PostMapping("/login")
	public String login(

			@Valid @ModelAttribute LoginRequest request,

			BindingResult result,

			HttpSession session,

			Model model) {

		if (result.hasErrors()) {

			model.addAttribute("loginRequest", request);

			return "login";
		}

		String response = userService.login(request);

		if (response.equals("SUCCESS")) {

			session.setAttribute("userEmail", request.getEmail());

			return "redirect:/dashboard";
		}

		model.addAttribute("msg", response);

		model.addAttribute("loginRequest", request);

		return "login";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {

		session.invalidate();

		return "redirect:/login";
	}

	@GetMapping("/forgot-password")
	public String loadForgotPassword(Model model) {

		model.addAttribute("forgotPasswordRequest", new ForgotPasswordRequest());

		return "forgot-password";
	}

	@PostMapping("/forgot-password")
	public String forgotPassword(

			@Valid @ModelAttribute ForgotPasswordRequest request,

			BindingResult result,

			Model model) {

		if (result.hasErrors()) {

			return "forgot-password";
		}

		String message = userService.forgotPassword(request.getEmail());

		model.addAttribute("msg", message);

		return "forgot-password";
	}

	@GetMapping("/reset-password")
	public String loadResetPasswordPage(@RequestParam String token, Model model) {

		ResetPasswordRequest request = new ResetPasswordRequest();

		request.setToken(token);

		model.addAttribute("resetPasswordRequest", request);

		return "reset-password";
	}

	@PostMapping("/reset-password")
	public String resetPassword(

			@Valid @ModelAttribute ResetPasswordRequest request,

			BindingResult result,

			Model model) {

		if (result.hasErrors()) {

			return "reset-password";
		}

		String message = userService.resetPassword(request);

		model.addAttribute("msg", message);

		model.addAttribute("loginRequest", new LoginRequest());

		return "login";
	}

	@GetMapping("/profile/edit")
	public String loadEditProfile(HttpSession session, Model model) {

		String email = (String) session.getAttribute("userEmail");

		UpdateProfileRequest request = userService.getProfileForEdit(email);

		model.addAttribute("profile", request);

		model.addAttribute("countries", locationService.getAllCountries());

		model.addAttribute("states", locationService.getStates(request.getCountryId()));

		model.addAttribute("cities", locationService.getCities(request.getStateId()));

		return "edit-profile";
	}

	@PostMapping("/profile/update")
	public String updateProfile(

			@Valid @ModelAttribute("profile") UpdateProfileRequest request,

			BindingResult result,

			Model model) {

		if (result.hasErrors()) {

			model.addAttribute("countries", locationService.getAllCountries());

			model.addAttribute("states", locationService.getStates(request.getCountryId()));

			model.addAttribute("cities", locationService.getCities(request.getStateId()));

			return "edit-profile";
		}

		userService.updateProfile(request);

		return "redirect:/dashboard";
	}
}
