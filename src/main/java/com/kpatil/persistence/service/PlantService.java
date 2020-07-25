package com.kpatil.persistence.service;

import com.kpatil.persistence.data.Plant;
import com.kpatil.persistence.repository.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PlantService {

    @Autowired
    private PlantRepository plantRepository;

    public PlantService(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    public Long save(Plant plant) {
        return plantRepository.save(plant).getId();
    }

    public Boolean delivered(Long id) {
        // return plantRepository.deliveryCompleted(id);
        return plantRepository.existsPlantByIdAndDeliveryCompleted(id, true);
    }

    public List<Plant> findPlantsBelowPrice(BigDecimal price) {
        return plantRepository.findByPriceLessThan(price);
    }
}
