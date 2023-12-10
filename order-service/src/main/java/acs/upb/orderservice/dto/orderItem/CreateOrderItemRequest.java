package acs.upb.orderservice.dto.orderItem;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderItemRequest {

  @NotNull private UUID productId;

  @NotNull private Integer quantity;
}
