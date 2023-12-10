package acs.upb.orderservice.dto.notification;

import acs.upb.orderservice.model.Order;
import acs.upb.orderservice.model.OrderItem;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class NotificationMapper {

  public static Notification orderToNotificationMappper(Order order, BigDecimal totalPrice) {
    return Notification.builder()
        .customerEmail(order.getCustomerEmail())
        .orderStatus(order.getOrderStatus())
        .city(order.getAddress().getCity())
        .district(order.getAddress().getDistrict())
        .addressDetail(order.getAddress().getAddressDetail())
        .orderItems(orderItemsToOrderItemNotification(order.getItems()))
        .totalPrice(totalPrice)
        .build();
  }

  public static List<OrderItemNotificationDto> orderItemsToOrderItemNotification(
      List<OrderItem> orderItemList) {
    return orderItemList.stream()
        .map(
            orderItem ->
                OrderItemNotificationDto.builder()
                    .productId(orderItem.getProductId())
                    .quantity(orderItem.getQuantity())
                    .build())
        .collect(Collectors.toList());
  }
}
