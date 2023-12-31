package com.freshvotes.web;

import com.freshvotes.repositories.UserRepository;
import com.freshvotes.security.SecurityUtil;
import org.apache.catalina.manager.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.SessionFactoryUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.freshvotes.domain.User;
import com.freshvotes.service.UserService;

@Controller
public class LoginController {
  @Autowired
  private UserService userService;
  
  @GetMapping("/login")
  public String login() {
    return "login";
  }
  
  @GetMapping("/register")
  public String register (ModelMap model) {
    model.put("user", new User());
    return "register";
  }
  
  @PostMapping("/register")
  public String registerPost (User user) {
    User savedUser = userService.save(user);
    
    return "redirect:/login";
  }
}
