package acs.upb.orderservice.client;

import acs.upb.orderservice.dto.inventory.InventoryCheckRequest;
import acs.upb.orderservice.dto.inventory.InventoryCheckResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "inventory-service", path = "/v1/inventory")
public interface InventoryClient {

  @PostMapping("/isInStock")
  ResponseEntity<InventoryCheckResponse> isInStock(
      List<InventoryCheckRequest> inventoryCheckRequests);
}
