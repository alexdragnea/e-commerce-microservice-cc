package acs.upb.inventoryservice.repository;

import acs.upb.inventoryservice.model.Inventory;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, UUID> {
  Long deleteByProductId(UUID productId);

  Inventory getByProductId(UUID productId);

  Inventory findByProductIdAndQuantityLessThan(UUID productId, Integer quantity);
}
