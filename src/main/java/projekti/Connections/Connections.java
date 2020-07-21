/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.Connections;

import projekti.Account.Account;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
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
public class Connections extends AbstractPersistable<Long> {
    @OneToOne(cascade = {CascadeType.ALL})
    private Account requester;
    
    @OneToOne(cascade = {CascadeType.ALL})
    private Account receiver;
}
