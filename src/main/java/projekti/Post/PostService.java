/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.Post;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.Account.Account;
import projekti.Account.AccountRepository;
import projekti.Account.AccountService;

/**
 *
 * @author mirka
 */
@Service
public class PostService {
    @Autowired
    AccountRepository accountRepository;
    
    @Autowired
    AccountService accountService;
    
    @Autowired
    PostRepository postRepository;
    
    @Autowired
    CommentRepository commentRepository;
    
    public List<Post> listForumposts(Account current) {
        List<Account> network = current.getConnections();
        
        List<Post> forumposts = current.getPosts();
        
        for(Account account : network) {
            for(Post post : account.getPosts()) {
                forumposts.add(post);
            } 
        }
        
        Collections.sort(forumposts);
        
        if(forumposts.size() > 25) {
            forumposts = forumposts.subList(0, 25);
        }
        
        return forumposts;
    }
    
    public void addNewPost(Post post) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        LocalDateTime currentTime = LocalDateTime.now();
        String formattedDate = currentTime.format(formatter);
        
        Account current = accountService.getCurrentUser();
        
        post.setSender(current.getName());
        post.setSent(formattedDate);
        post.setAccount(current);
        post.setTime(currentTime);
        
        postRepository.save(post);
        
        current.getPosts().add(post);
        accountRepository.save(current);
    }
    
    public void addNewComment(Comment comment, Long id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        LocalDateTime currentTime = LocalDateTime.now();
        String formattedDate = currentTime.format(formatter);
        
        Account current = accountService.getCurrentUser();
        
        comment.setSender(current.getName());
        comment.setSent(formattedDate);
        comment.setAccount(current);
        comment.setTime(currentTime);
        
        commentRepository.save(comment);
        
        Post post = postRepository.getOne(id);
        postRepository.getOne(id).getComments().add(comment);
        Collections.sort(post.getComments());
        
        postRepository.save(post);
    }
    
    public void addNewLike(Long id) {
        Account liker = accountService.getCurrentUser();
        Post liked = postRepository.getOne(id);
        
        // Check if already liked
        boolean alreadyLiked = false;
        if(liked.getLikers().contains(liker)) {
            alreadyLiked = true;
        }
        
        if(alreadyLiked == false) {
            liked.getLikers().add(liker);
            postRepository.save(liked);
        }
    }
}
