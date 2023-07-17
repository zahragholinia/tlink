package ir.tinyLink.model.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ApiErrorVO {
    private Date timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
