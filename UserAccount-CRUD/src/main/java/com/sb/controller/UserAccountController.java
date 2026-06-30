package com.sb.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.sb.entites.UserAccount;
import com.sb.service.UserAccountService;

@Controller
public class UserAccountController {

	private UserAccountService service;

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("user", new UserAccount());
		return "index";
	}

	@GetMapping("/save-user")
	public String handleSubmitBtn(@ModelAttribute("user") UserAccount user, Model model) {

		String msg = service.saveOrUpdateUserAcc(user);

		model.addAttribute("msg", msg);
		return "index";
	}

	@GetMapping("/users")
	public String getUsers(Model model) {
		List<UserAccount> UserList = service.getAllUserAccounts();
		model.addAttribute("users", UserList);
		return "view-users";
	}
	
	@GetMapping("/edit")
	public String editUser(@RequestParam("id") Integer id, Model model) {
		UserAccount userAcc = service.getUserAcc(id);
		model.addAttribute("user", userAcc);
		return "index";
	}
	
	@GetMapping("/delete")
	public String deleteUser(@RequestParam("id") Integer uid) {
		service.deleteUserAcc(uid);
		return "redirect:/users";
	}
	
	@GetMapping("/update")
	public String statusUpdate(@RequestParam("id") Integer uid, @RequestParam("status") String status) {
		service.updateUserAccStatus(uid, status);
		return "redirects:/users";
	}
}
