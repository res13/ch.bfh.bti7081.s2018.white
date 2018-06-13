package ch.bfh.bti7081.s2018.white.pms.common.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageHandler {

    private static Locale DEFAULT_LOCALE = new Locale("de", "CH");

    private static ResourceBundle MESSAGES = ResourceBundle.getBundle("MessageBundle", DEFAULT_LOCALE);
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
    public static final String SESSION_CLOSED = getMessage("SESSION_CLOSED");
    public static final String PLEASE_LOGIN_AGAIN = getMessage("PLEASE_LOGIN_AGAIN");
    public static final String EDIT = getMessage("EDIT");
    public static final String SAVED = getMessage("SAVED");
    public static final String NOT_SAVED = getMessage("NOT_SAVED");
    public static final String SAVED_COMMENT = getMessage("SAVED_COMMENT");
    public static final String DELETED = getMessage("DELETED");
    public static final String DELETED_COMMENT = getMessage("DELETED_COMMENT");
    public static final String DELETED_DIARY_ENTRY = getMessage("DELETED_DIARY_ENTRY");
    public static final String NEW_DIARY_ENTRY = getMessage("NEW_DIARY_ENTRY");
    public static final String NEW_COMMENT = getMessage("NEW_COMMENT");
    public static final String SAVED_DIARY_ENTRY = getMessage("SAVED_DIARY_ENTRY");
    public static final String NOT_SAVED_DIARY_ENTRY = getMessage("NOT_SAVED_DIARY_ENTRY");
    public static final String RELATIVE_DIARY = getMessage("RELATIVE_DIARY");
    public static final String PATIENT_DIARY = getMessage("PATIENT_DIARY");
    public static final String INVALID_USER_TYPE = getMessage("INVALID_USER_TYPE");
    public static final String PATIENT_READ = getMessage("PATIENT_READ");
    public static final String RELATIVE_READ = getMessage("RELATIVE_READ");
    public static final String RELATIVE = getMessage("RELATIVE");
    public static final String DOCTOR = getMessage("DOCTOR");
    public static final String PATIENT = getMessage("PATIENT");
    public static final String TARGET = getMessage("TARGET");
    public static final String STARTDATE = getMessage("STARTDATE");
    public static final String ENDDATE = getMessage("ENDDATE");
    public static final String DASHBOARD_NAME = getMessage("DASHBOARD");
    public static final String NOT_SAVED_COMMENT = getMessage("NOT_SAVED_COMMENT");
    public static final String WELCOME = getMessage("WELCOME");
    public static final String OVERVIEW_FOR = getMessage("OVERVIEW_FOR");
    public static final String COUNT_PATIENT = getMessage("COUNT_PATIENT");
    public static final String MY_DIARY_ENTRIES = getMessage("MY_DIARY_ENTRIES");
    public static final String PATIENTS_DIARY_ENTRIES = getMessage("PATIENTS_DIARY_ENTRIES");
    public static final String PATIENTS_GOALS = getMessage("PATIENTS_GOALS");
    public static final String CREATED_BY = getMessage("CREATED_BY");
    public static final String GOAL = getMessage("GOAL");
    public static final String SAVED_GOAL = getMessage("SAVED_GOAL");
    public static final String NOT_SAVED_GOAL = getMessage("NOT_SAVED_GOAL");
    public static final String OUT_OF_RANGE = getMessage("OUT_OF_RANGE");

    private static String getMessage(String messageId) {
        return MESSAGES.getString(messageId);
    }


}
