/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.Post;

import projekti.Account.AccountService;
import projekti.Account.Account;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author mirka
 */
@Controller
public class PostController {
    @Autowired
    AccountService accountService;
    
    @Autowired
    PostService postService;
    
    @GetMapping("/shared")
    public String viewShared(Model model) {
        Account current = accountService.getCurrentUser();
        List<Post> posts = postService.listForumposts(current);
        
        model.addAttribute("currentUser", current);
        model.addAttribute("post", new Post());
        model.addAttribute("posts", posts);
        model.addAttribute("comment", new Comment());
        
        return "shared";
    }
    
    @PostMapping("/shared")
    public String postComment(@Valid @ModelAttribute Post post, BindingResult bindingResult) {
        // Return form again if validation fails
        if(bindingResult.hasErrors()) {
            return "shared";
        }
        
        postService.addNewPost(post);
        
        return "redirect:/shared";
    }
    
    @PostMapping("/shared/{id}/comment")
    public String addComment(@Valid @ModelAttribute Comment comment, BindingResult bindingResult, @PathVariable Long id) {
        // Return form again if validation fails
        if(bindingResult.hasErrors()) {
            return "shared";
        }
        
        postService.addNewComment(comment, id);
        
        return "redirect:/shared";
    }
    
    @PostMapping("/shared/{id}/likes")
    public String addLikes(@PathVariable Long id) {        
        postService.addNewLike(id);

        return "redirect:/shared";
    }
}
