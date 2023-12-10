package acs.upb.userservice.enums;

import static acs.upb.userservice.constants.AUTHORITIES.ADMIN_AUTHORITIES;
import static acs.upb.userservice.constants.AUTHORITIES.USER_AUTHORITIES;

public enum Role {
  ROLE_USER(USER_AUTHORITIES),
  ROLE_ADMIN(ADMIN_AUTHORITIES);

  private final String[] authorities;

  Role(String... authorities) {
    this.authorities = authorities;
  }

  public String[] getAuthorities() {
    return authorities;
  }
}
