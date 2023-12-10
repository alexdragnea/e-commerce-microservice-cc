package acs.upb.notificationservice.controller;

import acs.upb.notificationservice.dto.Notification;
import acs.upb.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/notify")
public class NotificationController {

  private final NotificationService notificationService;

  @PostMapping
  public void notifyCustomer(@RequestBody Notification notification) {
    notificationService.sendEmail(notification);
  }
}
