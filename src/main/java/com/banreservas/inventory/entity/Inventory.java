package com.banreservas.inventory.entity;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;


@Entity
public class Inventory extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public Long productId;
    public Integer quantity;

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<InventoryMovement> movements;

    public Inventory() {  }

    public Inventory(Long id, Long productId, Integer quantity, List<InventoryMovement> movements) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.movements = movements;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Inventory inventory)) return false;
        return Objects.equals(id, inventory.id) && Objects.equals(productId, inventory.productId) && Objects.equals(quantity, inventory.quantity) && Objects.equals(movements, inventory.movements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, quantity, movements);
    }

    // Constructors, getters, and setters can be added here

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", movements=" + movements +
                '}';
    }
}

