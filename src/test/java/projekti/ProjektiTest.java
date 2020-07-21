package projekti;

import java.time.LocalDateTime;
import java.util.ArrayList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;
import projekti.Account.Account;
import projekti.Account.AccountRepository;
import projekti.Connections.Connections;
import projekti.Personal.Skill;
import projekti.Personal.SkillRepository;
import projekti.Post.Comment;
import projekti.Post.CommentRepository;
import projekti.Post.Post;
import projekti.Post.PostRepository;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjektiTest {
    @Autowired
    AccountRepository accountRepository;
    
    @Autowired
    PostRepository postRepository;
    
    @Autowired
    CommentRepository commentRepository;
    
    @Autowired
    SkillRepository skillRepository;
    
    @Test
    public void accountCreationWorks() {
        int startSize = accountRepository.findAll().size();
        
        Account account = createTestUser("name", "username", "password", "pagename");

        assertTrue(accountRepository.findAll().size() == startSize + 1);
    }
    
    @Test(expected = TransactionSystemException.class)
    public void accountCreationNotPossibleIfAlreadyExists() {
        int startSize = accountRepository.findAll().size();
        
        Account account = createTestUser("test", "test", "test", "test");
        Account account2 = createTestUser("test", "test", "test", "test");
    }
    
    @Test
    public void findAccountByUsernameWorks() {
        Account account = createTestUser("name2", "username2", "password2", "pagename2");
        
        Account findUser = accountRepository.findByUsername(account.getUsername());
        
        assertTrue(findUser.getUsername().equals("username2"));
    }
    
    @Test
    public void findAccountByPagenameWorks() {
        Account account = createTestUser("name3", "username3", "password3", "pagename3");
        
        Account findUser = accountRepository.findByPagename(account.getPagename());
        
        assertTrue(findUser.getPagename().equals("pagename3"));
    }
    
    @Test
    public void findAccountByIdWorks() {
        Account account = createTestUser("name4", "username4", "password4", "pagename4");
        
        Long firstId = account.getId();
        Long secondId = accountRepository.findByUsername("username4").getId();
        
        assertTrue(firstId.equals(secondId));
    }
    
    @Test
    public void createNewPostWorks() {
        int startSize = postRepository.findAll().size();
        Account account = createTestUser("name5", "username5", "password5", "pagename5");
        LocalDateTime time = LocalDateTime.now();
        
        Post post = new Post(account, null, null, "1", "jou", account.getName(), time);
        account.getPosts().add(post);
        postRepository.save(post);
        
        assertTrue(postRepository.findAll().size() == startSize + 1);
    }
    
    @Test
    public void addNewCommentWorks() {
        int startSize = commentRepository.findAll().size();
        Account account = createTestUser("name6", "username6", "password6", "pagename6");
        LocalDateTime time = LocalDateTime.now();
        
        Post post = new Post(account, null, null, "2", "jou2", account.getName(), time);
        account.getPosts().add(post);
        postRepository.save(post);
        
        Comment comment = new Comment(account, post, null, "1", "jou", time);
        account.getComments().add(comment);
        commentRepository.save(comment);
        
        assertTrue(commentRepository.findAll().size() == startSize + 1);
    }
    
    @Test 
    public void addNewSkillWorks() {
        int startSize = skillRepository.findAll().size();
        Account account = createTestUser("name7", "username7", "password7", "pagename7");
        
        Skill skill = new Skill(account, null, "jou", "jou", "jou");
        account.getSkills().add(skill);
        skillRepository.save(skill);
        
        assertTrue(skillRepository.findAll().size() == startSize + 1);     
    }
    
    @Test
    public void addNewConnectionRequestWorks() {
        Account requester = createTestUser("name8", "username8", "password8", "pagename8");
        Account receiver = createTestUser("name9", "username9", "password9", "pagename9");
        
        Connections connection = new Connections(requester, receiver);
        requester.getSentRequests().add(connection);
        receiver.getReceivedRequests().add(connection);
        
        accountRepository.save(requester);
        accountRepository.save(receiver);
        
        assertFalse(requester.getSentRequests().isEmpty());
        assertFalse(receiver.getReceivedRequests().isEmpty());
    }
    
    @Test
    public void addNewConnectionWorks() {
        Account account = createTestUser("name10", "username10", "password10", "pagename10");
        Account account2 = createTestUser("name11", "username11", "password11", "pagename11");
        
        account.getConnections().add(account2);
        account2.getConnections().add(account);
        
        accountRepository.save(account);
        accountRepository.save(account2);
        
        assertFalse(account.getConnections().isEmpty());
        assertFalse(account2.getConnections().isEmpty());
    }
    
    private Account createTestUser(String name, String username, String password, String pagename) {
        Account account = new Account();
        
        account.setSkills(new ArrayList<>());
        account.setPosts(new ArrayList<>());
        account.setComments(new ArrayList<>());
        account.setLikes(new ArrayList<>());
        account.setUpvotes(new ArrayList<>());
        account.setConnections(new ArrayList<>());
        account.setSentRequests(new ArrayList<>());
        account.setReceivedRequests(new ArrayList<>());
        account.setProfileImage(null);
        account.setName(name);
        account.setUsername(username);
        account.setPassword(password);
        account.setPagename(pagename);
        
        accountRepository.save(account);
        
        return account;
    }
    
}
