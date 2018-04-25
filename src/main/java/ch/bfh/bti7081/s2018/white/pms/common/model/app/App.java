package ch.bfh.bti7081.s2018.white.pms.common.model.app;

import ch.bfh.bti7081.s2018.white.pms.common.model.PmsType;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Role;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;

import java.util.List;

public abstract class App extends PmsType {

    private String name;

    private List<Role> readRole;

    private List<Role> updateRole;

    private List<User> userList;

}
