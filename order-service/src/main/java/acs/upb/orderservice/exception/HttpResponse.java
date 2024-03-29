package acs.upb.orderservice.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class HttpResponse {
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss", timezone = "Turkey")
  private Date timestamp;

  private int statusCode;
  private HttpStatus httpStatus;
  private String reason;
  private String message;

  public HttpResponse(int statusCode, HttpStatus httpStatus, String reason, String message) {
    this.timestamp = new Date();
    this.statusCode = statusCode;
    this.httpStatus = httpStatus;
    this.reason = reason;
    this.message = message;
  }
}
