package com.ums.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ProfileResponse {

	private Integer userId;

	private String firstName;

	private String lastName;

	private String email;

	private String phone;

	private LocalDate dob;

	private String gender;

	private Integer countryId;
	private String countryName;

	private Integer stateId;
	private String stateName;

	private Integer cityId;
	private String cityName;
}