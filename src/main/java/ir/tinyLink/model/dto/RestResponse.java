package ir.tinyLink.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Configuration for hibernate
 *
 * @author Zahra Gholinia
 * @since 2023-07-12
 */
@Builder
@Data
public class RestResponse<T> implements Serializable {

    private Integer status;
    private String error;
    private String message;
    private String path;
    private Date timestamp;
    private T result;
    private String reference;
    private Long count;

}