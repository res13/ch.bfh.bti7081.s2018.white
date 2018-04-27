package ch.bfh.bti7081.s2018.white.pms.common.model.user;

import ch.bfh.bti7081.s2018.white.pms.common.model.PmsType;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User extends PmsType {

    private String name;

    private String surname;

    private LocalDate dateOfBirth;

    private String email;

    private String passwordHash;

}
