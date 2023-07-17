package ir.tinyLink.message;

public class LinkMessage {

    private LinkMessage() {
    }

    public static String errorLinkNotFound() {
        return Translator.toLocale("link.not.found", null);
    }



}
