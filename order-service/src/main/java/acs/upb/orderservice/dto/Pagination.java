package acs.upb.orderservice.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Pagination<T> {
  private List<T> data;
  private long totalSize;
}
