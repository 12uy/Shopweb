package com.example.projectshop.controller;

import com.example.projectshop.dto.UserDTO;
import com.example.projectshop.global.GlobalData;
import com.example.projectshop.model.RoleModel;
import com.example.projectshop.model.UserModel;
import com.example.projectshop.repository.RoleRepository;
import com.example.projectshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/login")
    public String login(){
        GlobalData.cartList.clear();
        return "login";
    }

    @GetMapping("/register")
    public String GetRegister(){
        GlobalData.cartList.clear();
        return "register";
    }

    @GetMapping("/forgotpassword")
    public String forgotPassword(Model model){
        model.addAttribute(("userDTO"), new UserDTO());
        return "forgotpassword";
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute UserModel userModel,
                               HttpServletRequest request) throws ServletException {
        String password = userModel.getPassword();
        userModel.setPassword(bCryptPasswordEncoder.encode(password));
        List<RoleModel> roleModels = new ArrayList<>();
        roleModels.add(roleRepository.findById(Long.valueOf(1)).get());
        roleModels.add(roleRepository.findById(Long.valueOf(2)).get());
        userModel.setRoles(roleModels);
        userRepository.save(userModel);

        request.login(userModel.getEmail(), password);
        return "redirect:/";
    }

}
