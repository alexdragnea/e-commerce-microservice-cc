package acs.upb.inventoryservice.dto;

import acs.upb.inventoryservice.model.Inventory;
import org.springframework.stereotype.Component;

@Component
public class InventoryMapper {
  public Inventory createInventoryRequestToInventory(InventoryRequest inventoryRequest) {
    return Inventory.builder()
        .productId(inventoryRequest.getProductId())
        .productName(inventoryRequest.getProductName())
        .quantity(inventoryRequest.getQuantity())
        .price(inventoryRequest.getPrice())
        .build();
  }
}
