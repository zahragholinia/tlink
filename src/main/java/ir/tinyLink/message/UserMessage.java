package ir.tinyLink.message;

public class UserMessage {

    private UserMessage() {
    }

    public static String errorUserNotFound() {
        return Translator.toLocale("user.not.found", null);
    }
    public static String errorUserDuplicated(String username) {
        return Translator.toLocale("user.name.duplicate", new Object[]{username});
    }

    public static String errorInvalidPassword() {
        return Translator.toLocale("invalid.password",null);
    }



}
