package com.example.projectshop.service;

import com.example.projectshop.model.RoleModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface IRoleService {
    List<RoleModel> getAllRole();
    Optional<RoleModel> getRoleById(Long id);
}
