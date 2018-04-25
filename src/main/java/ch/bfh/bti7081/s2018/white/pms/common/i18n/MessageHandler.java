package ch.bfh.bti7081.s2018.white.pms.common.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageHandler {

    private static Locale DEFAULT_LOCALE = new Locale("de", "CH");

    private static ResourceBundle MESSAGES = ResourceBundle.getBundle("MessagesBundle", DEFAULT_LOCALE);

    private static String getMessage(String messageId) {
        return MESSAGES.getString(messageId);
    }

    public static final String PATIENT_DIARY_NAME = getMessage("PATIENT_DIARY_NAME");

    public static final String RELATIVE_DIARY_NAME = getMessage("RELATIVE_DIARY_NAME");


}
