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

}
