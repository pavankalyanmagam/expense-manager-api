package com.code.pavan.expensetrackerapi.service;

import com.code.pavan.expensetrackerapi.entity.User;
import com.code.pavan.expensetrackerapi.model.UserModel;

public interface UserService {

    User createUser(UserModel user);

    User read();

    User update(User user);

    void delete();

    User getLoggedInUser();
}
