package com.ums.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateProfileRequest {

	private Integer userId;

	@NotBlank(message = "First Name Required")
	private String firstName;

	@NotBlank(message = "Last Name Required")
	private String lastName;

	private String email;

	@NotBlank(message = "Phone Required")
	private String phone;

	@NotNull(message = "DOB Required")
	private LocalDate dob;

	@NotBlank(message = "Gender Required")
	private String gender;

	@NotNull(message = "Country Required")
	private Integer countryId;

	@NotNull(message = "State Required")
	private Integer stateId;

	@NotNull(message = "City Required")
	private Integer cityId;
}