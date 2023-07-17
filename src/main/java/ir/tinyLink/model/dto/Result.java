package ir.tinyLink.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.net.HttpURLConnection;

/**
 * Configuration for hibernate
 *
 * @author Zahra Gholinia
 * @since 2023-07-12
 */
@Data
@AllArgsConstructor
public class Result<T> {
    private int code;
    private String message;
    private T result;
    private boolean error;
    private long total;

    public Result() {
        this.code = HttpURLConnection.HTTP_OK;
        this.message = "ok.";
    }

    public Result(T result) {
        this();
        this.result = result;
    }

    public Result(int code, String message, T result) {
        this(result);
        this.code = code;
        this.message = message;
    }

}
