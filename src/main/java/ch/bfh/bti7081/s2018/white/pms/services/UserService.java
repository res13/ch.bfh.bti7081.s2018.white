package ch.bfh.bti7081.s2018.white.pms.services;

import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;

public interface UserService {

    User authenticate(String email, String password) throws Exception;

}
