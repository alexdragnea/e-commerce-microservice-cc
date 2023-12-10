package acs.upb.userservice.controller;

import static acs.upb.userservice.constants.SecurityConstant.TOKEN_PREFIX;
import static org.apache.hc.core5.http.HttpHeaders.AUTHORIZATION;

import acs.upb.userservice.dto.*;
import acs.upb.userservice.model.User;
import acs.upb.userservice.model.UserPrincipal;
import acs.upb.userservice.service.UserService;
import acs.upb.userservice.util.AuthenticationHelper;
import acs.upb.userservice.util.UtilityClass;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;
  private final AuthenticationHelper authenticationHelper;

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody RegisterUserRequest user) {
    userService.register(user);
    return ResponseEntity.ok("User is registered successfully");
  }

  @PostMapping("/register/admin")
  public ResponseEntity<String> registerAdmin(@RequestBody RegisterUserRequest userAdmin) {
    userService.registerAdmin(userAdmin);
    return ResponseEntity.ok("Admin user is registered succesfully");
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginUserRequest user) {
    authenticationHelper.authenticate(user.getEmail(), user.getPassword());
    User loginUser = userService.findUserByEmail(user.getEmail());
    UserPrincipal userPrincipal = new UserPrincipal(loginUser);
    LoginResponse loginResponse = authenticationHelper.getLoginResponse(userPrincipal);
    return ResponseEntity.ok(loginResponse);
  }

  @GetMapping("/token/refresh")
  public ResponseEntity<RefreshTokenResponse> refreshToken(
      HttpServletRequest request, HttpServletResponse response) throws IOException {

    String authorizationHeader = request.getHeader("refresh-token");
    return ResponseEntity.ok(
        authenticationHelper.validateRefreshToken(authorizationHeader, response));
  }

  @PostMapping("/validateToken")
  public ResponseEntity<UserDto> validateToken(@RequestParam String token) {
    return ResponseEntity.ok(userService.validateToken(token));
  }

  @GetMapping("/{userId}")
  public ResponseEntity<UserCredential> getUserById(
      @RequestHeader(AUTHORIZATION) String authorizationHeader, @PathVariable UUID userId) {
    MeDto meDto = userService.getMe(authorizationHeader.substring(TOKEN_PREFIX.length()));
    if (!UtilityClass.IsAdmin(meDto.getRoles())) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    return ResponseEntity.ok(userService.getUserCredentialsById(userId));
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<Void> deleteUser(
      @RequestHeader(AUTHORIZATION) String authorizationHeader, @PathVariable UUID userId) {
    MeDto meDto = userService.getMe(authorizationHeader.substring(TOKEN_PREFIX.length()));
    if (!UtilityClass.IsAdmin(meDto.getRoles())) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    userService.deleteUserById(userId);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @GetMapping("/me")
  public ResponseEntity<MeDto> getMe(@RequestHeader(AUTHORIZATION) String authorizationHeader) {
    String token = authorizationHeader.substring(TOKEN_PREFIX.length());
    return ResponseEntity.ok(userService.getMe(token));
  }
}
