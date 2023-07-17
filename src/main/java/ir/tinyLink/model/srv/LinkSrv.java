package ir.tinyLink.model.srv;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class LinkSrv implements Serializable {
    private String tinyLink;
    private String longLink;
    private Long id;
    private Integer view;

}
