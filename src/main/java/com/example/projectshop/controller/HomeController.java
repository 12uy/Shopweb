package com.example.projectshop.controller;

import com.example.projectshop.dto.UserDTO;
import com.example.projectshop.global.GlobalData;
import com.example.projectshop.model.RoleModel;
import com.example.projectshop.model.UserModel;
import com.example.projectshop.service.imp.CategoryServiceImpl;
import com.example.projectshop.service.imp.ProductServiceImpl;
import com.example.projectshop.service.imp.RoleServiceImpl;
import com.example.projectshop.service.imp.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;


    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private ProductServiceImpl productService;

    @GetMapping("/")
        public String home(Model model){
            model.addAttribute("cartCount", GlobalData.cartList.size());
            return "index";
        }

    @GetMapping("/users/add")
    public String updateUser(Model model){
        UserDTO currentUser= new UserDTO();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails && ((UserDetails) principal).getUsername() != null) {
            String currentUserName = ((UserDetails) principal).getUsername();
            UserModel user = userService.getUserByEmail(currentUserName).get();
            currentUser.setEmail(user.getEmail());
            currentUser.setId(Math.toIntExact(user.getId()));
            currentUser.setPassword("");
            currentUser.setFirstName(user.getFirstName());
            currentUser.setLastName(user.getLastName());
            List<Integer> roleIds = new ArrayList<>();
            for (RoleModel role : user.getRoles()) {
                roleIds.add(Math.toIntExact(role.getId()));
            }
            currentUser.setReleIds(roleIds);

        }
        model.addAttribute("userDTO", currentUser);
        return "userRoleAdd";
    }

    @PostMapping("/users/add")
    public String postUserAdd(@ModelAttribute("userDTO") UserDTO userDTO){
        UserModel user = new UserModel();
        user.setId(Long.valueOf(userDTO.getId()));
        user.setEmail(userDTO.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        List<RoleModel> roles = userService.getUserById(user.getId()).get().getRoles();
        user.setRoles(roles);
        userService.updateUser(user);
        return "index";
    }

    @GetMapping("/shop")
    public String Shop(Model model){
        model.addAttribute("cartCount", GlobalData.cartList.size());
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("products", productService.getAllProduct());
        return "shop";
    }

    @GetMapping("/shop/category/{id}")
    public String shopByCategory(@PathVariable("id") Long id, Model model){
        model.addAttribute("cartCount", GlobalData.cartList.size());
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("products", productService.getAllProductByCategoryId(id));
        return "shop";
    }

    @GetMapping("/shop/viewproduct/{id}")
    public String viewProduct(@PathVariable("id") Long id, Model model){
        model.addAttribute("cartCount", GlobalData.cartList.size());
        model.addAttribute("product", productService.getProductById(id).get());
        return "viewProduct";
    }


}
