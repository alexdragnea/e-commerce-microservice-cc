package acs.upb.inventoryservice.dto;

import java.util.List;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class MeDto {
  private String userId;
  private List<String> roles;
  private String email;
  private String firstName;
  private String lastName;
}
