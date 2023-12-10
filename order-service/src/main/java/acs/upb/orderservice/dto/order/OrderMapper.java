package acs.upb.orderservice.dto.order;

import acs.upb.orderservice.dto.orderAddress.OrderAddressMapper;
import acs.upb.orderservice.dto.orderItem.OrderItemMapper;
import acs.upb.orderservice.model.Order;
import acs.upb.orderservice.model.OrderStatus;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

  private final OrderAddressMapper orderAddressMapper;
  private final OrderItemMapper orderItemMapper;

  public OrderMapper(OrderAddressMapper orderAddressMapper, OrderItemMapper orderItemMapper) {
    this.orderAddressMapper = orderAddressMapper;
    this.orderItemMapper = orderItemMapper;
  }

  public OrderDto orderToOrderDto(Order order) {
    return OrderDto.builder()
        .id(order.getId())
        .customerId(order.getCustomerId())
        .customerEmail(order.getCustomerEmail())
        .address(orderAddressMapper.orderAddressToOrderAddressDto(order.getAddress()))
        .items(
            order.getItems().stream()
                .map(orderItemMapper::orderItemToOrderItemDto)
                .collect(Collectors.toList()))
        .orderStatus(order.getOrderStatus())
        .createdDate(order.getCreatedDate())
        .build();
  }

  public Order orderRequestToOrder(
      CreateOrderRequest createOrderRequest, UUID customerId, String customerEmail) {
    return Order.builder()
        .customerId(customerId)
        .customerEmail(customerEmail)
        .orderStatus(OrderStatus.APPROVED)
        .createdDate(LocalDateTime.now())
        .address(
            orderAddressMapper.orderAddressRequestToOrderAddress(createOrderRequest.getAddress()))
        .items(
            createOrderRequest.getItems().stream()
                .map(orderItemMapper::orderItemRequestToOrderItem)
                .collect(Collectors.toList()))
        .build();
  }
}
