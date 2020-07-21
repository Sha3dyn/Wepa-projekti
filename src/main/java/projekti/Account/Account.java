/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.Account;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import projekti.Post.Comment;
import projekti.Connections.Connections;
import projekti.Personal.Image;
import projekti.Post.Post;
import projekti.Personal.Skill;

/**
 *
 * @author mirka
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Account extends AbstractPersistable<Long> {
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Skill> skills = new ArrayList<>();
    
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();
    
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();
    
    @ManyToMany(mappedBy = "likers", fetch = FetchType.LAZY)
    private List<Post> likes;
    
    @ManyToMany(mappedBy = "upvoters", fetch = FetchType.LAZY)
    private List<Skill> upvotes;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "account_connections",
        joinColumns = @JoinColumn(name="account_id", referencedColumnName="id"),
        inverseJoinColumns = @JoinColumn(name="connected_id", referencedColumnName="id")
    )
    private List<Account> connections = new ArrayList<>();
    
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Connections> sentRequests = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Connections> receivedRequests = new ArrayList<>();
    
    @OneToOne
    private Image profileImage;
    
    @NotEmpty
    @Size(min = 4, max = 30)
    private String name;
    
    @NotEmpty
    @Size(min = 4, max = 30)
    @Column(unique = true)
    private String username;
    
    @NotEmpty
    @Size(min = 8, max = 75)
    private String password;
    
    @NotEmpty
    @Size(min = 5, max = 15)
    @Column(unique = true)
    private String pagename;
}
