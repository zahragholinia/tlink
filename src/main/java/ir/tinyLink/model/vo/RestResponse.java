package ir.tinyLink.model.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by z.gholinia on 2020/07/21 @PodBusinessPanel.
 */
@Setter
@Getter
public class RestResponse<T> implements Serializable {

	private Integer status;
	private String error;
	private String message;
	private String path;
	private Date timestamp;
	private T result;
	private String reference;
	private Long count;
	private String referenceNumber;
//	private String version;

	@Builder(builderMethodName = "Builder")
	public RestResponse(HttpStatus status, String path, String message, T result, String reference, Date timestamp, Long count) {
		this.status = status.value();
		this.error = status.isError() ? status.getReasonPhrase() : null;
		this.path = path;
		this.message = message;
		this.result = result;
		this.reference = reference;
		this.timestamp = timestamp;
		this.count = count;
//		this.version = getClass().getPackage().getImplementationVersion();
	}

	@Builder(builderClassName = "sBuilder")
	public RestResponse(int status, String path, String message, String reference, Date timestamp, String referenceNumber) {
		this.status = status;
		this.path = path;
		this.message = message;
		this.result = null;
		this.reference = reference;
		this.timestamp = timestamp;
		this.referenceNumber = referenceNumber;
//		this.version = getClass().getPackage().getImplementationVersion();
	}

	@Builder(builderClassName = "sBuilder")
	public RestResponse(int status, String error, String path, String message, String reference, Date timestamp) {
		this.status = status;
		this.error = error;
		this.path = path;
		this.message = message;
		this.result = null;
		this.reference = reference;
		this.timestamp = timestamp;
//		this.version = getClass().getPackage().getImplementationVersion();
	}
}