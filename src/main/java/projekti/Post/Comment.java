/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.Post;

import projekti.Account.Account;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
public class Comment extends AbstractPersistable<Long> implements Comparable<Comment>{
    @ManyToOne
    private Account account;
    
    @ManyToOne
    private Post post;

    private String sent;
    private String content;
    private String sender;
    private LocalDateTime time;
    
    @Override
    public int compareTo(Comment comment) {
        if (this.getTime().isBefore(comment.getTime())) {
            return 1;
        }

        if (this.getTime().isAfter(comment.getTime())) {
            return -1;
        }

        return 0;
    }
}
