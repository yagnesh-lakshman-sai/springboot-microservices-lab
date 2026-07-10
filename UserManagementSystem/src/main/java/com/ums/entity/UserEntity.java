package com.ums.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_master")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;

	private String firstName;

	private String lastName;

	@Column(unique = true)
	private String email;

	private String phone;

	private LocalDate dob;

	private String gender;

	private String password;

	private String accountStatus;

	@Column(name="verification_token")
	private String verificationToken;

	private LocalDateTime verificationExpiry;

	@Column(name="reset_token")
	private String resetToken;

	private LocalDateTime resetExpiry;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	@ManyToOne
	@JoinColumn(name = "country_id")
	private CountryEntity country;

	@ManyToOne
	@JoinColumn(name = "state_id")
	private StateEntity state;

	@ManyToOne
	@JoinColumn(name = "city_id")
	private CityEntity city;

}