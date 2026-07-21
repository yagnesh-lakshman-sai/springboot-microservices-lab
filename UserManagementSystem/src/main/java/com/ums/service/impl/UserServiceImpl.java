package com.ums.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ums.dto.CreatePasswordRequest;

import com.ums.dto.LoginRequest;
import com.ums.dto.ProfileResponse;
import com.ums.dto.RegistrationRequest;
import com.ums.dto.ResetPasswordRequest;
import com.ums.dto.UpdateProfileRequest;
import com.ums.entity.CityEntity;
import com.ums.entity.CountryEntity;
import com.ums.entity.StateEntity;
import com.ums.entity.UserEntity;
import com.ums.repository.CityRepository;
import com.ums.repository.CountryRepository;
import com.ums.repository.StateRepository;
import com.ums.repository.UserRepository;
import com.ums.service.EmailService;
import com.ums.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private CityRepository cityRepository;

	@Override
	public String registerUser(RegistrationRequest request) {

		if (userRepository.existsByEmail(request.getEmail())) {
			return "Email Already Registered";
		}

		String token = UUID.randomUUID().toString();

		UserEntity user = new UserEntity();

		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setEmail(request.getEmail());
		user.setPhone(request.getPhone());
		user.setDob(request.getDob());
		user.setGender(request.getGender());

		CountryEntity country = countryRepository.findById(request.getCountryId())
				.orElseThrow(() -> new RuntimeException("Country Not Found"));

		StateEntity state = stateRepository.findById(request.getStateId())
				.orElseThrow(() -> new RuntimeException("State Not Found"));

		CityEntity city = cityRepository.findById(request.getCityId())
				.orElseThrow(() -> new RuntimeException("City Not Found"));

		user.setCountry(country);
		user.setState(state);
		user.setCity(city);

		user.setAccountStatus("PENDING");
		user.setVerificationToken(token);
		user.setCreatedAt(LocalDateTime.now());

		userRepository.save(user);

		emailService.sendVerificationEmail(user.getEmail(), token);

		return "Registration Successful. Please check your email.";
	}

	@Override
	public boolean checkEmail(String email) {

		return userRepository.existsByEmail(email);

	}

	@Override
	public String verifyAccount(String token) {

		Optional<UserEntity> optional = userRepository.findByVerificationToken(token);

		if (optional.isEmpty()) {

			return "Invalid Verification Link";
		}

		UserEntity user = optional.get();

		user.setAccountStatus("ACTIVE");

		user.setVerificationToken(null);

		userRepository.save(user);

		return "Account Activated Successfully";

	}

	@Override
	public String createPassword(CreatePasswordRequest request) {

		if (!request.getPassword().equals(request.getConfirmPassword())) {
			return "Passwords do not match";
		}

		Optional<UserEntity> optional = userRepository.findByVerificationToken(request.getToken());

		if (optional.isEmpty()) {
			return "Invalid Token";
		}

		UserEntity user = optional.get();

		String encryptedPassword = passwordEncoder.encode(request.getPassword());

		user.setPassword(encryptedPassword);

		user.setVerificationToken(null);

		user.setAccountStatus("ACTIVE");

		userRepository.save(user);

		return "Password Created Successfully";
	}

	@Override
	public String login(LoginRequest request) {

		Optional<UserEntity> optional = userRepository.findByEmail(request.getEmail());

		if (optional.isEmpty()) {
			return "Invalid Email";
		}

		UserEntity user = optional.get();

		if (!"ACTIVE".equals(user.getAccountStatus())) {
			return "Account Not Activated";
		}

		boolean matched = passwordEncoder.matches(request.getPassword(), user.getPassword());

		if (!matched) {
			return "Invalid Password";
		}

		return "SUCCESS";
	}

	@Override
	public String forgotPassword(String email) {

		Optional<UserEntity> optional = userRepository.findByEmail(email);

		if (optional.isEmpty()) {

			return "Email Not Registered";
		}

		UserEntity user = optional.get();

		String token = UUID.randomUUID().toString();

		user.setResetToken(token);

		userRepository.save(user);

		emailService.sendResetPasswordEmail(user.getEmail(), token);

		return "Password reset link sent successfully.";
	}

	@Override
	public String resetPassword(ResetPasswordRequest request) {

		if (!request.getPassword().equals(request.getConfirmPassword())) {
			return "Passwords do not match";
		}

		Optional<UserEntity> optional = userRepository.findByResetToken(request.getToken());

		if (optional.isEmpty()) {
			return "Invalid or Expired Reset Link";
		}

		UserEntity user = optional.get();

		String encryptedPassword = passwordEncoder.encode(request.getPassword());

		user.setPassword(encryptedPassword);

		user.setResetToken(null);

		userRepository.save(user);

		return "Password Updated Successfully";
	}

	@Override
	public ProfileResponse getProfile(String email) {

		UserEntity user = userRepository.findByEmail(email).orElseThrow();

		ProfileResponse response = new ProfileResponse();

		response.setUserId(user.getUserId());

		response.setFirstName(user.getFirstName());

		response.setLastName(user.getLastName());

		response.setEmail(user.getEmail());

		response.setPhone(user.getPhone());

		response.setDob(user.getDob());

		response.setGender(user.getGender());

		response.setCountryId(user.getCountry().getCountryId());
		response.setCountryName(user.getCountry().getCountryName());

		response.setStateId(user.getState().getStateId());
		response.setStateName(user.getState().getStateName());

		response.setCityId(user.getCity().getCityId());
		response.setCityName(user.getCity().getCityName());

		return response;
	}

	@Override
	public UpdateProfileRequest getProfileForEdit(String email) {

		UserEntity user = userRepository.findByEmail(email).orElseThrow();

		UpdateProfileRequest dto = new UpdateProfileRequest();

		dto.setUserId(user.getUserId());

		dto.setFirstName(user.getFirstName());

		dto.setLastName(user.getLastName());

		dto.setEmail(user.getEmail());

		dto.setPhone(user.getPhone());

		dto.setDob(user.getDob());

		dto.setGender(user.getGender());

		dto.setCountryId(user.getCountry().getCountryId());

		dto.setStateId(user.getState().getStateId());

		dto.setCityId(user.getCity().getCityId());

		return dto;
	}

	@Override
	public String updateProfile(UpdateProfileRequest request) {

		UserEntity user = userRepository.findById(request.getUserId()).orElseThrow();

		user.setFirstName(request.getFirstName());

		user.setLastName(request.getLastName());

		user.setPhone(request.getPhone());

		user.setDob(request.getDob());

		user.setGender(request.getGender());

		CountryEntity country = countryRepository.findById(request.getCountryId()).orElseThrow();

		StateEntity state = stateRepository.findById(request.getStateId()).orElseThrow();

		CityEntity city = cityRepository.findById(request.getCityId()).orElseThrow();

		user.setCountry(country);

		user.setState(state);

		user.setCity(city);

		userRepository.save(user);

		return "SUCCESS";
	}
}
