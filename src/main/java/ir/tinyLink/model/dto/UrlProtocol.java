package ir.tinyLink.model.dto;

import ir.tinyLink.exception.TinyLinkGeneralException;
import ir.tinyLink.message.LinkMessage;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Configuration for hibernate
 *
 * @author Zahra Gholinia
 * @since 2023-07-12
 */
@Data
public class UrlProtocol {
    private String url;
    private String host;
    private int port;
    private String path;
    private String protocol;

    private String query;


    public UrlProtocol(String link) {
        try {
            URL url = new URL(link.startsWith("http") ? link : "http://" + link);
            this.url = link;
            this.host = url.getHost();
            this.protocol = url.getProtocol();
            this.port = url.getPort();
            this.path = url.getPath();
            this.query = url.getQuery();
        } catch (MalformedURLException e) {
            throw new TinyLinkGeneralException(HttpStatus.BAD_REQUEST, LinkMessage.errorLinkInvalid(url));
        }
    }

}
