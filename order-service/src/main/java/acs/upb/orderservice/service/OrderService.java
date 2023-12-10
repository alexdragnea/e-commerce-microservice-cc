package acs.upb.orderservice.service;

import acs.upb.orderservice.client.InventoryClient;
import acs.upb.orderservice.client.NotificationClient;
import acs.upb.orderservice.client.UserClient;
import acs.upb.orderservice.dto.MeDto;
import acs.upb.orderservice.dto.Pagination;
import acs.upb.orderservice.dto.inventory.InventoryCheckRequest;
import acs.upb.orderservice.dto.inventory.InventoryCheckResponse;
import acs.upb.orderservice.dto.notification.Notification;
import acs.upb.orderservice.dto.notification.NotificationMapper;
import acs.upb.orderservice.dto.order.CreateOrderRequest;
import acs.upb.orderservice.dto.order.OrderDto;
import acs.upb.orderservice.dto.order.OrderMapper;
import acs.upb.orderservice.exception.ProductNotInStockException;
import acs.upb.orderservice.model.Order;
import acs.upb.orderservice.repository.OrderRepository;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
  private final OrderRepository orderRepository;
  private final OrderMapper orderMapper;
  private final InventoryClient inventoryClient;
  private final UserClient userClient;
  private final NotificationClient notificationClient;

  public OrderDto createOrder(CreateOrderRequest createOrderRequest) {
    MeDto meDto = userClient.getLoggedInUserInfo().getBody();
    Order order =
        orderMapper.orderRequestToOrder(
            createOrderRequest, UUID.fromString(meDto.getUserId()), meDto.getEmail());

    log.info("Order created {}", order.toString());

    order.getAddress().setOrder(order);
    order.getItems().forEach(orderItem -> orderItem.setOrder(order));

    List<InventoryCheckRequest> inventoryCheckRequests =
        order.getItems().stream()
            .map(item -> new InventoryCheckRequest(item.getProductId(), item.getQuantity()))
            .collect(Collectors.toList());

    InventoryCheckResponse inventoryCheckResponse =
        inventoryClient.isInStock(inventoryCheckRequests).getBody();

    if (!Objects.requireNonNull(inventoryCheckResponse).getIsInStock()) {
      throw new ProductNotInStockException(
          "Product with ID: "
              + inventoryCheckResponse.getIsNotInStockProductIds().toString()
              + " is not in stock");
    }

    Notification notification =
        NotificationMapper.orderToNotificationMappper(
            order, inventoryCheckResponse.getTotalPrice());

    notificationClient.sendNotification(notification);
    return orderMapper.orderToOrderDto(orderRepository.save(order));
  }

  public void deleteOrder(UUID id) {
    orderRepository.deleteById(id);
  }

  public Pagination<OrderDto> getAllOrders(int pageNo, int pageSize) {
    Pageable paging = PageRequest.of(pageNo, pageSize);
    Page<Order> orders = orderRepository.findAll(paging);

    return new Pagination<>(
        orders.stream().map(orderMapper::orderToOrderDto).collect(Collectors.toList()),
        orders.getTotalElements());
  }
}
