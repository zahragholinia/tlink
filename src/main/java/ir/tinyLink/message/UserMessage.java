package ir.tinyLink.message;

/**
 * Configuration for hibernate
 *
 * @author Zahra Gholinia
 * @since 2023-07-12
 */

public class UserMessage {

    private UserMessage() {
    }

    public static String errorUserNotFound(String username) {
        return Translator.toLocale("user.not.found", new Object[]{username});
    }

    public static String errorUserNotFound() {
        return Translator.toLocale("user.not.found", null);
    }

    public static String errorUserDuplicated(String username) {
        return Translator.toLocale("user.name.duplicate", new Object[]{username});
    }

}
