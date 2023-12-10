package acs.upb.orderservice.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.*;

@Data
@NoArgsConstructor
public class FeignErrorResponse {
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private Date timeStamp;

  private int httpStatusCode;
  private int httpStatus;
  private String reason;

  public FeignErrorResponse(int httpStatusCode, int httpStatus, String reason) {
    this.timeStamp = new Date();
    this.httpStatusCode = httpStatusCode;
    this.httpStatus = httpStatus;
    this.reason = reason;
  }
}
