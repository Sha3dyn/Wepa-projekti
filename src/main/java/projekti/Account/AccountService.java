/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author mirka
 */
@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    
    public Account getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account currentUser = accountRepository.findByUsername(auth.getName());
        
        return currentUser;
    }
    
    public Account getPageOwner(String pagename) {
        Account user = accountRepository.findByPagename(pagename);
        
        return user;
    }
    
    public void userRegistration(Account account) {
        String encodedPw = new BCryptPasswordEncoder().encode(account.getPassword());
        account.setPassword(encodedPw);
        
        account.setProfileImage(null);
        
        accountRepository.save(account);
    }
}
