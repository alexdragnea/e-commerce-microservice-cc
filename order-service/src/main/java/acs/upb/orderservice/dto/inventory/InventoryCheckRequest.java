package acs.upb.orderservice.dto.inventory;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryCheckRequest {
  private UUID productId;
  private Integer quantity;
}
