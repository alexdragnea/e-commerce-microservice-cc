package acs.upb.inventoryservice.service;

import acs.upb.inventoryservice.client.UserServiceClient;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService {

  private final UserServiceClient userServiceClient;

  public boolean IsAdmin() {
    List<String> userRoles = userServiceClient.getLoggedInUserInfo().getBody().getRoles();

    log.info("Role {}", userRoles.toString());
    return userRoles.contains("ROLE_ADMIN");
  }
}
