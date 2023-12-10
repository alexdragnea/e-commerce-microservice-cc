package acs.upb.notificationservice.service;

import acs.upb.notificationservice.dto.Notification;
import acs.upb.notificationservice.dto.OrderItemNotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class NotificationService {

  private final JavaMailSender emailSender;

  public void sendEmail(Notification notification) {

    log.info("Notification received, notification object: {}", notification);

    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("noreply@acs.upb-ecommerce.com");
    message.setTo(notification.getCustomerEmail());
    message.setSubject("Order Notification");
    message.setText(buildEmailText(notification));
    emailSender.send(message);

    log.info("Email sent to: {}, email content: {}", message.getTo(), message.getText());
  }

  private String buildEmailText(Notification notification) {
    StringBuilder emailText = new StringBuilder();
    emailText.append("Dear Customer,\n\n");
    emailText.append("Your order has been processed successfully.\n");
    emailText.append("Order Details:\n");

    for (OrderItemNotificationDto orderItem : notification.getOrderItems()) {
      emailText.append(
          String.format(
              "- Product ID: %s, Quantity: %d\n",
              orderItem.getProductId(), orderItem.getQuantity()));
    }
    emailText.append("\nTotal Price: ").append(notification.getTotalPrice()).append("\n");

    // Include order status and address details
    emailText.append("\nOrder Status: ").append(notification.getOrderStatus()).append("\n");
    emailText.append("Delivery Address:\n");
    emailText.append("- City: ").append(notification.getCity()).append("\n");
    emailText.append("- District: ").append(notification.getDistrict()).append("\n");
    emailText.append("- Address: ").append(notification.getAddressDetail()).append("\n");

    emailText.append("\nThank you for shopping with us!");

    return emailText.toString();
  }
}
