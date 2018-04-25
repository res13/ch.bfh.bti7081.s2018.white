package ch.bfh.bti7081.s2018.white.pms.services;

import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;

public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Override
    public long authenticate(String username, String password) {
        return 0;
    }
}
