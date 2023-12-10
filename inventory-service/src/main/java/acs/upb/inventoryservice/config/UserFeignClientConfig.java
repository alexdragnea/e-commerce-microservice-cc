package acs.upb.inventoryservice.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class UserFeignClientConfig {
  @Bean
  public RequestInterceptor requestInterceptor() {
    return template -> {
      ServletRequestAttributes attributes =
          (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

      if (attributes != null) {

        attributes
            .getRequest()
            .getHeaderNames()
            .asIterator()
            .forEachRemaining(
                headerName ->
                    template.header(headerName, attributes.getRequest().getHeader(headerName)));
      }
    };
  }
}
