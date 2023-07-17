package ir.tinyLink.model.vo;

import org.springframework.http.HttpStatus;

import java.io.Serializable;


public class ResFact<T> implements Serializable {

    private Result restResponse;

    private ResFact(Result restResponse) {
        this.restResponse = restResponse;
        //Do NOT CREATE
    }

    public static <T> ResFact<T> build() {
        return new ResFact<T>(new Result<T>());
    }

    public ResFact<T> setResult(T result) {
        this.restResponse.setResult(result);
        return this;
    }

    public ResFact<T> setError(boolean hasError) {
        this.restResponse.setError(hasError);
        return this;
    }

    public ResFact<T> setMessage(String message) {
        this.restResponse.setMessage(message);
        return this;
    }

    public ResFact<T> setCode(HttpStatus status) {
        this.restResponse.setCode(status.value());
        return this;
    }

    public ResFact<T> setTotal(long total) {
        this.restResponse.setTotal(total);
        return this;
    }

    public ResFact<T> setItemsWithTotal(ItemsWithTotal itemsWithTotal) {
        this.restResponse.setResult(itemsWithTotal.getItems());
        this.restResponse.setTotal(itemsWithTotal.getTotal());
        return this;
    }

    public Result<T> get() {
        return restResponse;
    }

}
