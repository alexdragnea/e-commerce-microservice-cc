package acs.upb.orderservice.model;

import acs.upb.orderservice.model.common.BaseModel;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "orderItems")
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class OrderItem extends BaseModel {

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "order_id")
  private Order order;

  private UUID productId;
  private Integer quantity;
}
