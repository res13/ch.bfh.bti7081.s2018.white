package ch.bfh.bti7081.s2018.white.pms.common.model.app;

import ch.bfh.bti7081.s2018.white.pms.common.model.PmsEntity;
import ch.bfh.bti7081.s2018.white.pms.common.model.caze.Caze;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class App extends PmsEntity {

    protected String name;

    @ManyToOne
    private Caze caze;

    public App() {
    }

    public void notify(List<User> userList, String title, String message) {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Caze getCaze() {
        return caze;
    }

    public void setCaze(Caze caze) {
        this.caze = caze;
    }
}
