package acs.upb.userservice.util;

import java.util.List;

public class UtilityClass {
  public static boolean IsAdmin(List<String> userRoles) {

    return userRoles.contains("ROLE_ADMIN");
  }
}
