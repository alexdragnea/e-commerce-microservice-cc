package acs.upb.inventoryservice.dto;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryRequest {

  private transient UUID productId = UUID.randomUUID();
  private String productName;
  private Integer quantity;
  private BigDecimal price;
}
