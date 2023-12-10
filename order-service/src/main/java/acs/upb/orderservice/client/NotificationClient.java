package acs.upb.orderservice.client;

import acs.upb.orderservice.dto.notification.Notification;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service", path = "/v1/notify")
public interface NotificationClient {

  @PostMapping
  void sendNotification(@RequestBody Notification notification);
}
