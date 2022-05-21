package com.example.projectshop.service;

import com.example.projectshop.model.UserModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface IUserService {
    List<UserModel> getAllUser();
    void updateUser(UserModel user);
    void removeUserById(Long id);
    Optional<UserModel> getUserById(Long id);
    Optional<UserModel> getUserByEmail(String email);
}
