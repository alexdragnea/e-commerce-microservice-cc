package acs.upb.inventoryservice.exception;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FeignExceptionHandler {
  @ExceptionHandler(FeignException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public FeignErrorResponse handleFeignException(FeignException ex) {

    return new FeignErrorResponse(ex.status(), ex.status(), ex.getMessage());
  }
}
