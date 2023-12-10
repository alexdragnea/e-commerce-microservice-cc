package acs.upb.orderservice.dto.notification;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemNotificationDto {
  private UUID productId;
  private Integer quantity;
  private BigDecimal price;
}
