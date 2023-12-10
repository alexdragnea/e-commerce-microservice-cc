package acs.upb.orderservice.utility;

import acs.upb.orderservice.client.UserClient;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService {

  private final UserClient userClient;

  public boolean IsAdmin() {
    List<String> userRoles = userClient.getLoggedInUserInfo().getBody().getRoles();

    log.info("Role {}", userRoles.toString());
    return userRoles.contains("ROLE_ADMIN");
  }
}
