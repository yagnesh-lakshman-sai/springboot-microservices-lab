package com.ums.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegistrationRequest {

	@NotBlank(message = "First Name is Required")
	private String firstName;

	@NotBlank(message = "Last Name is Required")
	private String lastName;

	@NotBlank(message = "Email is Required")
	@Email(message = "Enter Valid Email")
	private String email;

	@NotBlank(message = "Phone Number is Required")
	private String phone;

	@NotNull(message = "Date Of Birth is Required")
	private LocalDate dob;

	@NotBlank(message = "Gender is Required")
	private String gender;

	@NotNull(message = "Country is Required")
	private Integer countryId;

	@NotNull(message = "State is Required")
	private Integer stateId;

	@NotNull(message = "City is Required")
	private Integer cityId;

}
