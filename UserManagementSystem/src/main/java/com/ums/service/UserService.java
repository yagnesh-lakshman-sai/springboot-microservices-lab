package com.ums.service;

import com.ums.dto.CreatePasswordRequest;
import com.ums.dto.LoginRequest;
import com.ums.dto.ProfileResponse;
import com.ums.dto.RegistrationRequest;
import com.ums.dto.ResetPasswordRequest;
import com.ums.dto.UpdateProfileRequest;

public interface UserService {

	String registerUser(RegistrationRequest request);

	boolean checkEmail(String email);

	String verifyAccount(String token);

	String createPassword(CreatePasswordRequest request);

	String login(LoginRequest request);

	String forgotPassword(String email);

	String resetPassword(ResetPasswordRequest request);

	ProfileResponse getProfile(String email);

	UpdateProfileRequest getProfileForEdit(String email);

	String updateProfile(UpdateProfileRequest request);
}
