/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.Personal;

import projekti.Account.Account;
import projekti.Account.AccountRepository;
import projekti.Account.AccountService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import projekti.Connections.ConnectionsService;

/**
 *
 * @author mirka
 */
@Controller
public class PersonalController {
    @Autowired
    AccountRepository accountRepository;
    
    @Autowired
    AccountService accountService;
    
    @Autowired
    ImageRepository imageRepository;
    
    @Autowired
    PersonalService personalService;
    
    @Autowired
    ConnectionsService connectionsService;
    
    // Generate personal page path
    @GetMapping("/personal")
    public String redirectPersonalPage() {
        Account currentUser = accountService.getCurrentUser();
        
        return "redirect:/personal/" + currentUser.getPagename();
    }
    
    // Personal view
    @GetMapping("/personal/{pagename}")
    public String showUserPage(@PathVariable String pagename, Model model) {
        Account viewedAccount = accountService.getPageOwner(pagename);
        Account currentUser = accountService.getCurrentUser();
        
        Map<Skill, Integer> sortedSkills = personalService.sortSkills(viewedAccount.getSkills());
        List<Skill> topSkills = personalService.mostUpvotedSkills(sortedSkills);

        model.addAttribute("pageOwner", viewedAccount);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("pagename", pagename);
        model.addAttribute("skill", new Skill());
        model.addAttribute("skills", sortedSkills);
        model.addAttribute("topSkills", topSkills);
        
        return "personal";
    }
    
    // Add new skills
    @PostMapping("/personal/{pagename}")
    public String addSkill(@Valid @ModelAttribute Skill skill, BindingResult bindingResult) {
        // Return form again if validation fails
        if(bindingResult.hasErrors()) {
            return "personal";
        }

        personalService.addNewSkill(skill);
        
        return "redirect:/personal";
    }
    
    // Upvote skills
    @PostMapping("/personal/{pagename}/skill/{id}/upvotes")
    public String addUpvotes(@PathVariable String pagename, @PathVariable Long id) {
        personalService.addNewUpvote(id);

        return "redirect:/personal/" + pagename;
    }
    
    @GetMapping(path = "image/{id}", produces = {"image/png", "image/jpeg"})
    @ResponseBody
    public byte[] getProfileImageContent(@PathVariable Long id) {
        return imageRepository.getOne(id).getContent();
    }
    
    @PostMapping("/personal/{pagename}/image")
    public String changeProfileImage(@PathVariable String pagename, @RequestParam("file") MultipartFile file) throws IOException {
        Account currentAccount = accountService.getCurrentUser();
        
        if(file.getContentType().equals("image/png") || file.getContentType().equals("image/jpeg")) {
            Image image = new Image();
        
            image.setContent(file.getBytes());
            imageRepository.save(image);

            currentAccount.setProfileImage(image);
            accountRepository.save(currentAccount); 
        }    
        
        return "redirect:/personal";
    }
}
