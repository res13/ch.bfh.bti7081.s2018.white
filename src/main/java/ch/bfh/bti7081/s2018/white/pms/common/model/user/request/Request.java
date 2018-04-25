package ch.bfh.bti7081.s2018.white.pms.common.model.user.request;

import ch.bfh.bti7081.s2018.white.pms.common.model.PmsType;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;

public class Request extends PmsType {

    private User requestUser;

    private User targetUser;

    private RequestType type;

    private RequestState state;

}
