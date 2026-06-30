package com.sb.service;

import java.util.List;

import com.sb.entites.UserAccount;

public interface UserAccountService {

    String saveOrUpdateUserAcc(UserAccount userAcc);

    List<UserAccount> getAllUserAccounts();

    UserAccount getUserAcc(Integer userId);

    boolean deleteUserAcc(Integer userId);

    boolean updateUserAccStatus(Integer userId, String status);

}
