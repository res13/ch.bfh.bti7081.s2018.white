package ch.bfh.bti7081.s2018.white.pms.common.model.user;

import ch.bfh.bti7081.s2018.white.pms.common.model.PmsType;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.App;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.request.Request;

import java.time.LocalDate;
import java.util.List;

public class User extends PmsType {

    private String name;

    private String surname;

    private LocalDate dateOfBirth;

    private String email;

    private String passwordHash;

    private Role role;

    private List<App> appList;

    private List<User> userList;

    private List<Request> openRequestList;

}
