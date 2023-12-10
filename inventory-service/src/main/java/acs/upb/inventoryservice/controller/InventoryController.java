package acs.upb.inventoryservice.controller;

import acs.upb.inventoryservice.dto.InventoryCheckRequest;
import acs.upb.inventoryservice.dto.InventoryCheckResponse;
import acs.upb.inventoryservice.dto.InventoryRequest;
import acs.upb.inventoryservice.service.InventoryService;
import acs.upb.inventoryservice.service.RoleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

  private final InventoryService inventoryService;
  private final RoleService roleService;

  @PutMapping
  public ResponseEntity<Void> updateInventory(@RequestBody InventoryRequest inventoryRequest) {

    if (roleService.IsAdmin()) {
      inventoryService.updateProductFromInventory(inventoryRequest);
      return ResponseEntity.status(HttpStatus.OK).build();
    }

    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }

  @PostMapping
  public ResponseEntity<Void> createInventory(@RequestBody InventoryRequest inventoryRequest) {

    if (roleService.IsAdmin()) {
      inventoryService.addProductToInventory(inventoryRequest);
      return ResponseEntity.status(HttpStatus.OK).build();
    }
    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }

  @PostMapping("/isInStock")
  public ResponseEntity<InventoryCheckResponse> isInStock(
      @RequestBody List<InventoryCheckRequest> inventoryCheckRequests) {
    return ResponseEntity.ok(inventoryService.isInStock(inventoryCheckRequests));
  }
}
