/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.Personal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author mirka
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Image extends AbstractPersistable<Long> {
    //@Lob // Remove for Heroku
    //@Basic(fetch = FetchType.LAZY) // Remove for Heroku
    @Column(name = "content", columnDefinition="LONGBLOB") // To remove too long value error
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] content;
}
