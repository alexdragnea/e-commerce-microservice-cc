package acs.upb.notificationservice.dto;

import acs.upb.notificationservice.constant.OrderStatus;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

  List<OrderItemNotificationDto> orderItems;
  private String customerEmail;
  private OrderStatus orderStatus;
  private String city;
  private String district;
  private String addressDetail;
  private BigDecimal totalPrice;
}
