package com.example.projectshop.service.imp;

import com.example.projectshop.model.RoleModel;
import com.example.projectshop.repository.RoleRepository;
import com.example.projectshop.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<RoleModel> getAllRole() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<RoleModel> getRoleById(Long id) {
        return roleRepository.findById(id);
    }
}
