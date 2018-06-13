package ch.bfh.bti7081.s2018.white.pms.ui.common;

import ch.bfh.bti7081.s2018.white.pms.common.i18n.MessageHandler;

public enum ButtonType {

    NEW_COMMENT("images/plus.png", MessageHandler.NEW_COMMENT),
    NEW_GOAL("images/plus.png", MessageHandler.ADD_GOAL),
    NEW_DIARY_ENTRY("images/plus.png", MessageHandler.NEW_DIARY_ENTRY),
    EDIT("images/edit.png", MessageHandler.EDIT),
    DELETE("images/delete.png", MessageHandler.DELETE),
    SAVE("images/save.png", MessageHandler.SAVE),
    LOGIN("images/login.png", MessageHandler.LOGIN);

    String image;

    String message;

    ButtonType(String image, String message) {
        this.image = image;
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public String getMessage() {
        return message;
    }
}
