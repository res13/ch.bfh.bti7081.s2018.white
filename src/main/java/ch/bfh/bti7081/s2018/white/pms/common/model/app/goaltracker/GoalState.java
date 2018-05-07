package ch.bfh.bti7081.s2018.white.pms.common.model.app.goaltracker;

import ch.bfh.bti7081.s2018.white.pms.common.i18n.MessageHandler;

public enum GoalState {
    OPEN(MessageHandler.OPEN),
    SUCCESSFUL(MessageHandler.SUCCESSFUL),
    MISSED(MessageHandler.MISSED);

    private String message;

    GoalState(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

}
