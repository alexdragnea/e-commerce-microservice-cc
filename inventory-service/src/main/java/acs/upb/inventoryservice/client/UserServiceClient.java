package acs.upb.inventoryservice.client;

import acs.upb.inventoryservice.config.UserFeignClientConfig;
import acs.upb.inventoryservice.dto.MeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "user-service", path = "/v1/user", configuration = UserFeignClientConfig.class)
public interface UserServiceClient {

  @GetMapping("/me")
  ResponseEntity<MeDto> getLoggedInUserInfo();
}
