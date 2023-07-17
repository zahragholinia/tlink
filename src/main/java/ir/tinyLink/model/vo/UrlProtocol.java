package ir.tinyLink.model.vo;

import lombok.*;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@Data
public class UrlProtocol {
    private String url;
    private String host;
    private int port;
    private String path;
    private String protocol;

    private String query;


    public UrlProtocol(String url) throws MalformedURLException {
        URL url1=new URL(url);
        this.url=url;
        this.host=url1.getHost();
        this.protocol=url1.getProtocol();
        this.port=url1.getPort();
        this.path=url1.getPath();
        this.query=url1.getQuery();
    }

}
