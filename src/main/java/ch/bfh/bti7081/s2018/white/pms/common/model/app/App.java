package ch.bfh.bti7081.s2018.white.pms.common.model.app;

import ch.bfh.bti7081.s2018.white.pms.common.model.PmsType;
import ch.bfh.bti7081.s2018.white.pms.common.model.caze.Case;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;

import java.util.List;

public abstract class App extends PmsType {

    protected String name;

    private Case caze;

    public void notify(List<User>userList, String title, String message) {

    }
}
