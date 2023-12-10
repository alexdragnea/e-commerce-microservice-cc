package acs.upb.orderservice.controller;

import acs.upb.orderservice.dto.Pagination;
import acs.upb.orderservice.dto.order.CreateOrderRequest;
import acs.upb.orderservice.dto.order.OrderDto;
import acs.upb.orderservice.service.OrderService;
import acs.upb.orderservice.utility.RoleService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/orders")
@Slf4j
@RequiredArgsConstructor
public class OrderController {
  private final OrderService orderService;
  private final RoleService roleService;

  @PostMapping
  public ResponseEntity<OrderDto> createOrder(
      @Valid @RequestBody CreateOrderRequest createOrderRequest) {
    log.info("Create order request was called");
    return new ResponseEntity<>(orderService.createOrder(createOrderRequest), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<Pagination<OrderDto>> getAllOrders(
      @RequestParam(required = false, defaultValue = "0") int pageNo,
      @RequestParam(required = false, defaultValue = "10") int pageSize) {
    if (roleService.IsAdmin()) {
      return ResponseEntity.ok(orderService.getAllOrders(pageNo, pageSize));
    }

    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrder(@PathVariable UUID id) {

    if (roleService.IsAdmin()) {
      orderService.deleteOrder(id);
      return ResponseEntity.ok().build();
    }

    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }
}
