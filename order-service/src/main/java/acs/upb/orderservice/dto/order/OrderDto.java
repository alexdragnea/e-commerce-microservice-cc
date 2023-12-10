package acs.upb.orderservice.dto.order;

import acs.upb.orderservice.dto.orderAddress.OrderAddressDto;
import acs.upb.orderservice.dto.orderItem.OrderItemDto;
import acs.upb.orderservice.model.OrderStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
  private UUID id;
  private UUID customerId;
  private String customerEmail;
  private OrderAddressDto address;
  private List<OrderItemDto> items;
  private OrderStatus orderStatus;
  private LocalDateTime createdDate;
}
