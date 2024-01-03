package com.freshvotes.web;

import java.security.Principal;
import java.util.List;

import com.freshvotes.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;
import com.freshvotes.repositories.ProductRepository;

@Controller
public class DashboardController {

    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String rootView() {
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard(ModelMap model,@AuthenticationPrincipal User user) {
        Long userId = getUserId(user);
        List<Product> products = productRepo.findAll();
        model.put("products", products);
        model.put("userId",userId);
        return "dashboard";
    }

    private Long getUserId(@AuthenticationPrincipal User user) {
        return userRepository.findById(user.getId()).get().getId();
    }
}
