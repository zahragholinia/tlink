package ir.tinyLink.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Configuration for hibernate
 *
 * @author Zahra Gholinia
 * @since 2023-07-12
 */
@Data
@Builder
public class LinkSrv implements Serializable {
    private String tinyLink;
    private String longLink;
    private Long id;
    private Integer view;

}
