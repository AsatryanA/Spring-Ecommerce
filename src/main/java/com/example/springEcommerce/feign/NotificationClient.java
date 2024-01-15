package com.example.springEcommerce.feign;

import com.example.springEcommerce.model.dto.NotificationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "notification-service", url = "http://localhost:8081/notification")
public interface NotificationClient {
    @PostMapping
    void notify(@RequestHeader(value = "Authorization") String token, @RequestBody NotificationDTO notificationDTO);
}
