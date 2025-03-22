package com.banreservas.inventory.messaging;

import java.io.Serializable;

public class InventoryEvent implements Serializable {
    private String productId;
    private String status;

    public InventoryEvent(String productId, String status) {
        this.productId = productId;
        this.status = status;
    }

    // Getters y Setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "InventoryEvent{productId='" + productId + "', status='" + status + "'}";
    }
}

