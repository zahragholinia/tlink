package ir.tinyLink.message;

public class LinkMessage {

    private LinkMessage() {
    }

    public static String errorLinkNotFound(String link) {
        return Translator.toLocale("link.not.found", new Object[]{link});
    }

    public static String errorLinkInvalid(String link) {
        return Translator.toLocale("link.invalid", new Object[]{link});
    }

    public static String errorLinkTooMany(int limit) {
        return Translator.toLocale("link.limit.insert", new Object[]{limit});
    }


}
