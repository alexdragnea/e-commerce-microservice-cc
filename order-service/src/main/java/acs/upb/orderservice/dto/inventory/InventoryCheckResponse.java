package acs.upb.orderservice.dto.inventory;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryCheckResponse {

  private List<UUID> isNotInStockProductIds;
  private Boolean isInStock;
  private BigDecimal totalPrice;
}
