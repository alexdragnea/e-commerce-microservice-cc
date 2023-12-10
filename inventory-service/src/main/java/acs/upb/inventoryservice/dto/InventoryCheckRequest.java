package acs.upb.inventoryservice.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryCheckRequest {
  private UUID productId;
  private Integer quantity;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class DeleteInventoryRequest {
    private UUID productId;
  }
}
