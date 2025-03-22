package com.banreservas.inventory.repository;

import com.banreservas.inventory.entity.Inventory;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InventoryRepository implements PanacheRepository<Inventory> {

    public Inventory findByProductId(Long productId) {
        return find("productId", productId).firstResult();
    }

    public void adjustQuantity(Long productId, int quantityChange) {
        Inventory inventory = findByProductId(productId);
        if (inventory == null) {
            inventory = new Inventory();
            inventory.productId = productId;
            inventory.quantity = 0;
        }
        inventory.quantity += quantityChange;
        persist(inventory);
    }
}

