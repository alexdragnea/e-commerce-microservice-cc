package acs.upb.inventoryservice.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class InventoryCheckResponse {

  private List<UUID> isNotInStockProductIds;
  private Boolean isInStock;
  private BigDecimal totalPrice;
}
