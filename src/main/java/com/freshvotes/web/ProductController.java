package com.freshvotes.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;
import com.freshvotes.repositories.ProductRepository;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepo;

    @GetMapping("/products/{productId}")
    public String getProduct(@PathVariable Long productId, ModelMap model, HttpServletResponse response) throws IOException {
        Product product = productRepo.findProductById(productId);

        if (!(product == null)) {
            model.put("product", product);
        } else {
            response.sendError(HttpStatus.NOT_FOUND.value(), "Product with id " + productId + " was not found");
            return "product";
        }
        return "product";
    }

    @GetMapping("/p/{productName}")
    public String findProductByName(@PathVariable String productName, Model model) {
        if (productName != null) {
            try {
                String decoded = URLDecoder.decode(productName, StandardCharsets.UTF_8.name());
                Product product = productRepo.findProductByName(decoded);
                if (product!= null){
                    model.addAttribute("product",product);
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return "productUserView";
    }

    @PostMapping("/products/{productId}")
    public String saveProduct(@PathVariable Long productId, Product product) {
        productRepo.save(product);
        return "redirect:/dashboard";
    }

    @PostMapping("/products")
    public String createProduct(@AuthenticationPrincipal User user) {
        Product product = new Product();
        product.setPublished(false);
        product.setUser(user);
        productRepo.save(product);
        return "redirect:/products/" + product.getId();
    }
}
