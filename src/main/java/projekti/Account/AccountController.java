/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.Account;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author mirka
 */
@Controller
public class AccountController {
    @Autowired
    AccountService accountService;
    
    @ModelAttribute
    private Account getAccount() {
        return new Account();
    } 
    
    @GetMapping("/login")
    public String loginUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if(auth instanceof AnonymousAuthenticationToken) {
            return "login";
        }
        
        return "redirect:/personal";
    }
    
    @GetMapping("/register")
    public String viewRegisterForm() {
    
        return "register";
    }
    
    @PostMapping("/register")
    public String registerNewUser(@Valid @ModelAttribute Account account, BindingResult bindingResult) {
        // Return form again if validation fails
        if(bindingResult.hasErrors()) {
            return "register";
        }
        
        accountService.userRegistration(account);
        
        return "redirect:/login";
    }
}
