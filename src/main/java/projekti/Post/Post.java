/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.Post;

import projekti.Account.Account;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author mirka
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Post extends AbstractPersistable<Long> implements Comparable<Post>{
    @ManyToOne
    private Account account;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();
    
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Account> likers = new ArrayList<>();
    
    private String sent;
    private String content;
    private String sender;
    private LocalDateTime time;
    
    @Override
    public int compareTo(Post post) {
        if (this.getTime().isBefore(post.getTime())) {
            return 1;
        }

        if (this.getTime().isAfter(post.getTime())) {
            return -1;
        }

        return 0;
    }
}
