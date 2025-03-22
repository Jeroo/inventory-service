package com.banreservas.inventory.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class InventoryMovement extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public Long productId;
    public int quantityChange;
    public String movementType; // "IN" or "OUT"
    public LocalDateTime movementDate;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    public Inventory inventory;


    // Constructor vacío para JPA
    public InventoryMovement() {}

    // Constructor con parámetros
    public InventoryMovement(Long productId, int quantityChange, String movementType, LocalDateTime movementDate) {
        this.productId = productId;
        this.quantityChange = quantityChange;
        this.movementType = movementType;
        this.movementDate = movementDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryMovement that = (InventoryMovement) o;
        return Objects.equals(id, that.id) && Objects.equals(inventory, that.inventory) && Objects.equals(quantityChange, that.quantityChange) && Objects.equals(movementType, that.movementType) && Objects.equals(movementDate, that.movementDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, inventory, quantityChange, movementType, movementDate);
    }

    // Getters y setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantityChange() {
        return quantityChange;
    }

    public void setQuantityChange(int quantityChange) {
        this.quantityChange = quantityChange;
    }

    public String getMovementType() {
        return movementType;
    }

    public void setMovementType(String movementType) {
        this.movementType = movementType;
    }

    public LocalDateTime getMovementDate() {
        return movementDate;
    }

    public void setMovementDate(LocalDateTime movementDate) {
        this.movementDate = movementDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
