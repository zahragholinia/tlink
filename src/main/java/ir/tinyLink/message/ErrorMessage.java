package ir.tinyLink.message;

public class ErrorMessage {

    private ErrorMessage() {
    }

    public static String errorInternalServer() {
        return Translator.toLocale("error.internal.server", null);
    }


    public static String errorUnauthorized() {
        return Translator.toLocale("error.internal.server", null);
    }

    public static String errorAtProcessingRequest() {
        return Translator.toLocale("error.process.request", null);
    }

    public static String errorResourceConflict() {
        return Translator.toLocale("error.resource.conflict", null);
    }

    public static String unknownError() {
        return Translator.toLocale("error.unknown", null);
    }
    public static String errorAtReadFile() {
        return Translator.toLocale("error.read-file", null);
    }

}
