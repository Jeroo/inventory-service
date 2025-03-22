package com.banreservas.inventory.repository;

import com.banreservas.inventory.entity.InventoryMovement;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class InventoryMovementRepository implements PanacheRepository<InventoryMovement> {

    public List<InventoryMovement> findByProductId(long productId) {
        return list("productId", productId);
    }
}
