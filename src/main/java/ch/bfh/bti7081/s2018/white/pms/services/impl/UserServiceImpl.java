package ch.bfh.bti7081.s2018.white.pms.services.impl;

import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;
import ch.bfh.bti7081.s2018.white.pms.services.UserService;

public abstract class UserServiceImpl<T extends User> extends BaseServiceImpl<T> implements UserService {

    @Override
    public long authenticate(String email, String password) {
        return 0;
    }
}
