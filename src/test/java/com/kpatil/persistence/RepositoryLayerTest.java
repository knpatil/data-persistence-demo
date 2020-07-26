package com.kpatil.persistence;

import com.kpatil.persistence.data.Delivery;
import com.kpatil.persistence.data.Plant;
import com.kpatil.persistence.repository.PlantRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RepositoryLayerTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private PlantRepository plantRepository;

    @Test
    void testPriceLessThan() {
        Plant p = testEntityManager.persist(new Plant("Jasmine", 5.99));
        testEntityManager.persist(new Plant("Geranium", 7.99));

        List<Plant> cheapPlants = plantRepository.findByPriceLessThan(BigDecimal.valueOf(7.98));

        assertThat(cheapPlants.size()).isEqualTo(1);
        assertThat(cheapPlants.get(0).getId()).isEqualTo(p.getId());
    }

    void testDeliveryCompleted() {
        Plant p = testEntityManager.persist(new Plant("Jasmine Root", 19.99));
        Delivery d = testEntityManager.persist(new Delivery("Leonardo Di", "1 East Side", LocalDateTime.now()));

        d.setPlants(Lists.newArrayList(p));
        p.setDelivery(d);

        //test both before and after
        Assertions.assertFalse(plantRepository.deliveryCompleted(p.getId()));
        d.setCompleted(true);
        Assertions.assertTrue(plantRepository.deliveryCompleted(p.getId()));
    }
}
