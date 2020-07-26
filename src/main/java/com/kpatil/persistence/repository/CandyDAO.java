package com.kpatil.persistence.repository;

import com.kpatil.persistence.dto.CandyData;

import java.util.List;

public interface CandyDAO {
    List<CandyData> list();

    void addToDelivery(Long candyId, Long deliveryId);

    List<CandyData> findByDelivery(Long deliveryId);
}
