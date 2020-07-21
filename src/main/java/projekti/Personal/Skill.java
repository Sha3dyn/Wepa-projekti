/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.Personal;

import projekti.Account.Account;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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
public class Skill extends AbstractPersistable<Long> {
    @ManyToOne
    private Account account;
    
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Account> upvoters = new ArrayList<>();
    
    @NotEmpty
    @Size(min = 1, max = 50)
    private String title;
    
    @NotEmpty
    @Size(min = 1, max = 50)
    private String level;
    
    private String info;
}
