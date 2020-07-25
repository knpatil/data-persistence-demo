package com.kpatil.persistence.repository;

import com.kpatil.persistence.data.Delivery;
import com.kpatil.persistence.data.Plant;
import com.kpatil.persistence.dto.RecipientAndPrice;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class DeliveryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void persist(Delivery delivery) {
        this.entityManager.persist(delivery);
    }

    public Delivery find(Long id) {
        return this.entityManager.find(Delivery.class, id);
    }

    public Delivery merge(Delivery delivery) {
        return this.entityManager.merge(delivery);
    }

    public void delete(Long id) {
        Delivery delivery = this.entityManager.find(Delivery.class, id);
        this.entityManager.remove(delivery);
    }

    public List<Delivery> findDeliveriesByName(String name) {
        TypedQuery<Delivery> query = entityManager.createNamedQuery("Delivery.findByName", Delivery.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    // One possible way to solve this - query a list of Plants with deliveryId matching
    // the one provided and sum their prices.
    public RecipientAndPrice getBill(Long deliveryId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<RecipientAndPrice> query = cb.createQuery(RecipientAndPrice.class);
        Root<Plant> root = query.from(Plant.class);
        query.select(
                cb.construct(
                        RecipientAndPrice.class,
                        root.get("delivery").get("name"),
                        cb.sum(root.get("price"))))
                .where(cb.equal(root.get("delivery").get("id"), deliveryId));
        return entityManager.createQuery(query).getSingleResult();
    }
    /*

    Hibernate:
    Resultant Hibernate-generated Query from CriteriaBuilder:

    select
        delivery1_.name as col_0_0_,
        sum(plant0_.price) as col_1_0_
    from
        plant plant0_ cross
    join
        delivery delivery1_
    where
        plant0_.delivery_id=delivery1_.id
        and plant0_.delivery_id=3

     */
}
