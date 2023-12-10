package acs.upb.userservice.service;

import static acs.upb.userservice.constants.SecurityConstant.AUTHORITIES;
import static acs.upb.userservice.enums.Role.ROLE_ADMIN;
import static acs.upb.userservice.enums.Role.ROLE_USER;

import acs.upb.userservice.dto.*;
import acs.upb.userservice.exception.EmailExistException;
import acs.upb.userservice.exception.EmailNotFoundException;
import acs.upb.userservice.model.User;
import acs.upb.userservice.model.UserPrincipal;
import acs.upb.userservice.repository.UserRepository;
import acs.upb.userservice.util.JWTTokenProvider;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.transaction.Transactional;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final BCryptPasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final LoginAttemptService loginAttemptService;
  private final JWTTokenProvider jwtTokenProvider;

  @Override
  public UserDetails loadUserByUsername(String email) {
    User user = findUserByEmail(email);
    validateLoginAttempt(user);
    userRepository.save(user);
    return new UserPrincipal(user);
  }

  public User register(RegisterUserRequest request) {
    validateEmail(request.getEmail());
    User newUser = new User();
    newUser.setFirstName(request.getFirstName());
    newUser.setLastName(request.getLastName());
    newUser.setEmail(request.getEmail());
    newUser.setPassword(passwordEncoder.encode(request.getPassword()));
    newUser.setRole(ROLE_USER.name());
    newUser.setAuthorities(ROLE_USER.getAuthorities());
    userRepository.save(newUser);

    return newUser;
  }

  public User registerAdmin(RegisterUserRequest request) {
    validateEmail(request.getEmail());
    User userAdmin = new User();
    userAdmin.setFirstName(request.getFirstName());
    userAdmin.setLastName(request.getLastName());
    userAdmin.setEmail(request.getEmail());
    userAdmin.setPassword(passwordEncoder.encode(request.getPassword()));
    userAdmin.setRole(ROLE_ADMIN.name());
    userAdmin.setAuthorities(ROLE_ADMIN.getAuthorities());
    userRepository.save(userAdmin);

    return userAdmin;
  }

  public UserDto validateToken(String token) {
    DecodedJWT decodedJWT = jwtTokenProvider.decodeToken(token);
    String userId = decodedJWT.getClaim("userId").asString();
    String username =
        decodedJWT.getClaim("firstName").asString()
            + " "
            + decodedJWT.getClaim("lastName").asString();
    List<GrantedAuthority> authorities = jwtTokenProvider.getAuthorities(decodedJWT);
    return new UserDto(userId, authorities, username);
  }

  public User getUserById(UUID id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("User could not found by id " + id));
  }

  public User findUserByEmail(String email) {
    return userRepository
        .findUserByEmail(email)
        .orElseThrow(() -> new EmailNotFoundException("No user found for email: " + email));
  }

  private void validateLoginAttempt(User user) {

    loginAttemptService.evictUserFromLoginAttemptCache(user.getEmail());
  }

  private void validateEmail(String email) {
    Optional<User> userByNewEmail = userRepository.findUserByEmail(email);
    if (userByNewEmail.isPresent()) {
      throw new EmailExistException("Email already exists.");
    }
  }

  public UserCredential getUserCredentialsById(UUID id) {
    User user = userRepository.getById(id);
    return new UserCredential(
        user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
  }

  public MeDto getMe(String token) {
    DecodedJWT decodedJWT = jwtTokenProvider.decodeToken(token);

    List<String> roles = decodedJWT.getClaim(AUTHORITIES).asList(String.class);
    String userId = decodedJWT.getClaim("userId").asString();
    String firstName = decodedJWT.getClaim("firstName").asString();
    String lastName = decodedJWT.getClaim("lastName").asString();
    String email = decodedJWT.getClaim("email").asString();

    return MeDto.builder()
        .firstName(firstName)
        .lastName(lastName)
        .email(email)
        .userId(userId)
        .roles(roles)
        .build();
  }

  @Transactional
  public void deleteUserById(UUID id) {
    userRepository.deleteById(id);
  }
}
