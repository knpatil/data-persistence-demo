package com.kpatil.persistence.service;

import com.kpatil.persistence.data.Delivery;
import com.kpatil.persistence.dto.RecipientAndPrice;
import com.kpatil.persistence.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    public Long save(Delivery delivery) {
        delivery.getPlants().forEach(plant -> plant.setDelivery(delivery));
        deliveryRepository.persist(delivery);
        return delivery.getId();
    }

    public RecipientAndPrice getBill(Long deliveryId) {
        return deliveryRepository.getBill(deliveryId);
    }
}
