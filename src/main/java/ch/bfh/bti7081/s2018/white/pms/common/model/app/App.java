package ch.bfh.bti7081.s2018.white.pms.common.model.app;

import ch.bfh.bti7081.s2018.white.pms.common.model.PmsType;
import ch.bfh.bti7081.s2018.white.pms.common.model.caze.Case;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class App extends PmsType {

    protected String name;

    @ManyToOne
    private Case caze;

    public void notify(List<User>userList, String title, String message) {

    }

    public App() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Case getCaze() {
        return caze;
    }

    public void setCaze(Case caze) {
        this.caze = caze;
    }
}
