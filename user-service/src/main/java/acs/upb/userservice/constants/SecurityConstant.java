package acs.upb.userservice.constants;

public class SecurityConstant {
  public static final long EXPIRATION_TIME =
          (24 * 60 * 60 * 1000); // 1 days expressed in milliseconds

  public static final long REFRESH_TOKEN_EXP =
          (24 * 60 * 60 * 1000); // 1 days expressed in milliseconds
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String Company_LLC = "E-Commerce-UPB";
  public static final String Company_ADMINISTRATION = "E Commerce Portal";
  public static final String AUTHORITIES = "authorities";
}
