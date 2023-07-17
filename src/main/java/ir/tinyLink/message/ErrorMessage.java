package ir.tinyLink.message;

public class ErrorMessage {

    private ErrorMessage() {
    }

    public static String errorInternalServer() {
        return Translator.toLocale("error.internal.server", null);
    }

    public static String errorValidation() {
        return Translator.toLocale("error.validation", null);
    }

    public static String errorTypeField(String field,String type) {
        return Translator.toLocale("error.type.field", new Object[]{field,type});
    }

    public static String errorUnauthorized() {
        return Translator.toLocale("error.unauthorized", null);
    }

}
