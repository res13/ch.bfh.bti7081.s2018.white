package ch.bfh.bti7081.s2018.white.pms.common.model;

import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@MappedSuperclass
public abstract class PmsType {

    @Id
    @GeneratedValue
    private long id;

    private LocalDate lastModified;

    @OneToOne
    private User lastModifiedBy;

}
