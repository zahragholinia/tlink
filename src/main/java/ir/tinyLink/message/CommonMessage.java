package ir.tinyLink.message;

/**
 * Configuration for hibernate
 *
 * @author Zahra Gholinia
 * @since 2023-07-12
 */

public class CommonMessage {

    private CommonMessage() {
    }

    public static String serverIsNotAccessible(String serverName) {
        return Translator.toLocale("server.not.accessible", new Object[]{serverName});
    }

    public static String ok() {
        return Translator.toLocale("ok", null);
    }

    public static String paramRequired(String paramName) {
        return Translator.toLocale("param.required", new Object[]{paramName});
    }

    public static String paramHasInvalidFormat(String paramName) {
        return Translator.toLocale("param.format.invalid", new Object[]{paramName});
    }

    public static String paramMustBeGreaterThanValue(String paramName, String value) {
        return Translator.toLocale("param.must.greater.than", new Object[]{paramName, value});
    }

    public static String paramMustBeLessThanValue(String paramName, String value) {
        return Translator.toLocale("param.must.less.than", new Object[]{paramName, value});
    }

    public static String paramMustBeMultiplyByValue(String paramName, String value) {
        return Translator.toLocale("param.must.multiple.by", new Object[]{paramName, value});
    }

    public static String resourceNotFound(String resource) {
        return Translator.toLocale("resource.not.found", new Object[]{resource});
    }

    public static String resourceCreated(String resource) {
        return Translator.toLocale("resource.created", new Object[]{resource});
    }

    public static String resourceAdded(String resource) {
        return Translator.toLocale("resource.added", new Object[]{resource});
    }

    public static String resourceUpdated(String resource) {
        return Translator.toLocale("resource.updated", new Object[]{resource});
    }

    public static String resourceDeleted(String resource) {
        return Translator.toLocale("resource.deleted", new Object[]{resource});
    }

    public static String duplicateName(String resource) {
        return Translator.toLocale("resource.name.duplicate", new Object[]{resource});
    }

    public static String resourceRemovedSuccessfully(String resource) {
        return Translator.toLocale("resource.remove", new Object[]{resource});
    }

    public static String paramMinimumLength(String paramName, String minimumLength) {
        return Translator.toLocale("param.length.min.limit", new Object[]{paramName, minimumLength});
    }

    public static String paramMaximumLength(String paramName, String maximumLength) {
        return Translator.toLocale("param.length.max.limit", new Object[]{paramName, maximumLength});
    }

    public static String paramMustNotBeBlank(String paramName) {
        return Translator.toLocale("param.blank", new Object[]{paramName});
    }

}
