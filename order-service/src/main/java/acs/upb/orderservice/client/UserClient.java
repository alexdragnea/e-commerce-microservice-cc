package acs.upb.orderservice.client;

import acs.upb.orderservice.config.UserFeignClientConfig;
import acs.upb.orderservice.dto.MeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "user-service", path = "/v1/user", configuration = UserFeignClientConfig.class)
public interface UserClient {

  @GetMapping("/me")
  ResponseEntity<MeDto> getLoggedInUserInfo();
}
