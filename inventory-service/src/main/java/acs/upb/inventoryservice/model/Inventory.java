package acs.upb.inventoryservice.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "inventories")
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private UUID productId;
  private String productName;
  private Integer quantity;
  private BigDecimal price;
}
