package com.example.projectshop.configuration;

import com.example.projectshop.model.RoleModel;
import com.example.projectshop.model.UserModel;
import com.example.projectshop.repository.RoleRepository;
import com.example.projectshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        String email = token.getPrincipal().getAttributes().get("email").toString();
        if (userRepository.findUserModelByEmail(email).isPresent()) {

        }else {
            UserModel userModel = new UserModel();
            userModel.setFirstName(token.getPrincipal().getAttributes().get("given_name").toString());
            userModel.setLastName(token.getPrincipal().getAttributes().get("family_name").toString());
            userModel.setEmail(email);
            userModel.setPassword(bCryptPasswordEncoder.encode("1"));
            List<RoleModel> roles = new ArrayList<>();
            roles.add(roleRepository.findById(Long.valueOf(2)).get());
            userModel.setRoles(roles);
            userRepository.save(userModel);
        }
        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/");
    }
}
