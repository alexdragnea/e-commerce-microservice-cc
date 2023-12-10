package acs.upb.orderservice.dto.order;

import acs.upb.orderservice.dto.orderAddress.CreateOrderAddressRequest;
import acs.upb.orderservice.dto.orderItem.CreateOrderItemRequest;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;

@Data
public class CreateOrderRequest {
  @NotNull private CreateOrderAddressRequest address;
  @NotNull private List<CreateOrderItemRequest> items;
}
