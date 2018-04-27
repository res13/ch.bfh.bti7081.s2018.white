package ch.bfh.bti7081.s2018.white.pms.common.model;

import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
public abstract class PmsType {

    @Id
    @GeneratedValue
    private long id;

    private Date lastModified;

    @OneToOne
    private User lastModifiedBy;

}
