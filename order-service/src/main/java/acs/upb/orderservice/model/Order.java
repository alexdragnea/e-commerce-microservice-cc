package acs.upb.orderservice.model;

import acs.upb.orderservice.model.common.AdvanceBaseModal;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "orders")
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Order extends AdvanceBaseModal {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  private UUID id;

  private UUID customerId;

  private String customerEmail;

  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

  @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
  private OrderAddress address;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<OrderItem> items;
}
