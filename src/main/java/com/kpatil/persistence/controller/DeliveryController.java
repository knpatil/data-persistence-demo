package com.kpatil.persistence.controller;

import com.kpatil.persistence.data.Delivery;
import com.kpatil.persistence.dto.RecipientAndPrice;
import com.kpatil.persistence.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {
    @Autowired
    private DeliveryService deliveryService;

    @PostMapping
    public Long scheduleDelivery(@RequestBody Delivery delivery) {
        System.out.println("Received post request ...");
        return deliveryService.save(delivery);
    }

    @GetMapping("/bill/{deliveryId}")
    public RecipientAndPrice getBill(@PathVariable Long deliveryId) {
        return deliveryService.getBill(deliveryId);
    }
}
