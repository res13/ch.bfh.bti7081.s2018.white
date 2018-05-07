package ch.bfh.bti7081.s2018.white.pms.common.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageHandler {

    private static Locale DEFAULT_LOCALE = new Locale("de", "CH");

    private static ResourceBundle MESSAGES = ResourceBundle.getBundle("MessageBundle", DEFAULT_LOCALE);

    private static String getMessage(String messageId) {
        return MESSAGES.getString(messageId);
    }

    public static final String DIARY_NAME = getMessage("DIARY_NAME");

    public static final String GOAL_TRACKER_NAME = getMessage("GOAL_TRACKER_NAME");

    public static final String USERNAME_OR_PASSWORD_INCORRECT = getMessage("USERNAME_OR_PASSWORD_INCORRECT");

    public static final String EMAIL_ALREADY_IN_USE = getMessage("EMAIL_ALREADY_IN_USE");

    public static final String APPS = getMessage("APPS");

    public static final String EMAIL = getMessage("EMAIL");

    public static final String LOGIN = getMessage("LOGIN");

    public static final String LOGOUT = getMessage("LOGOUT");

    public static final String PASSWORD = getMessage("PASSWORD");

    public static final String PROFILE = getMessage("PROFILE");

    public static final String REGISTRATE = getMessage("REGISTRATE");

    public static final String SETTINGS = getMessage("SETTINGS");

    public static final String FILTER = getMessage("FILTER");

    public static final String CLEAR_FILTER = getMessage("CLEAR_FILTER");

    public static final String ADD_GOAL = getMessage("ADD_GOAL");

    public static final String NAME = getMessage("NAME");

    public static final String SURNAME = getMessage("SURNAME");

    public static final String BIRTHDAY = getMessage("BIRTHDAY");

    public static final String STATUS = getMessage("STATUS");

    public static final String SAVE = getMessage("SAVE");

    public static final String DELETE = getMessage("DELETE");

    public static final String OPEN = getMessage("OPEN");

    public static final String SUCCESSFUL = getMessage("SUCCESSFUL");

    public static final String MISSED = getMessage("MISSED");


}
