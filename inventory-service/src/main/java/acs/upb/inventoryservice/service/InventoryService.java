package acs.upb.inventoryservice.service;

import acs.upb.inventoryservice.dto.InventoryCheckRequest;
import acs.upb.inventoryservice.dto.InventoryCheckResponse;
import acs.upb.inventoryservice.dto.InventoryMapper;
import acs.upb.inventoryservice.dto.InventoryRequest;
import acs.upb.inventoryservice.model.Inventory;
import acs.upb.inventoryservice.repository.InventoryRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
  private final InventoryRepository inventoryRepository;
  private final InventoryMapper inventoryMapper;

  public void addProductToInventory(InventoryRequest inventoryRequest) {
    inventoryRepository.save(inventoryMapper.createInventoryRequestToInventory(inventoryRequest));
  }

  @Transactional
  public void deleteProductFromInventory(
      InventoryCheckRequest.DeleteInventoryRequest deleteInventoryRequest) {
    inventoryRepository.deleteByProductId(deleteInventoryRequest.getProductId());
  }

  public void updateProductFromInventory(InventoryRequest inventoryRequest) {
    Inventory inventory = inventoryRepository.getByProductId(inventoryRequest.getProductId());
    inventory.setQuantity(inventoryRequest.getQuantity());
    inventoryRepository.save(inventory);
  }

  public InventoryCheckResponse isInStock(List<InventoryCheckRequest> inventoryCheckRequests) {
    List<UUID> notInStockProductIds = new ArrayList<>();
    BigDecimal totalOrderPrice = BigDecimal.ZERO;

    for (InventoryCheckRequest inventoryRequest : inventoryCheckRequests) {
      Inventory existingInventory =
          inventoryRepository.getByProductId(inventoryRequest.getProductId());

      if (existingInventory == null
          || existingInventory.getQuantity() < inventoryRequest.getQuantity()) {
        notInStockProductIds.add(inventoryRequest.getProductId());
      } else {
        BigDecimal productPrice;
        productPrice = existingInventory.getPrice();
        totalOrderPrice =
            totalOrderPrice.add(
                productPrice.multiply(BigDecimal.valueOf(inventoryRequest.getQuantity())));

        existingInventory.setQuantity(
            existingInventory.getQuantity() - inventoryRequest.getQuantity());
        inventoryRepository.save(existingInventory);
      }
    }

    return InventoryCheckResponse.builder()
        .isNotInStockProductIds(notInStockProductIds)
        .isInStock(notInStockProductIds.isEmpty())
        .totalPrice(totalOrderPrice)
        .build();
  }
}
