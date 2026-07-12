package com.ums.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordRequest {

	@NotBlank(message = "Password is Required")
	private String password;

	@NotBlank(message = "Confirm Password is Required")
	private String confirmPassword;

	private String token;

}