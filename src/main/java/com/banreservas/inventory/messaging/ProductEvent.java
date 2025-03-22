package com.banreservas.inventory.messaging;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.io.Serializable;

public class ProductEvent implements Serializable {
    private String productId;
    private String type;

    public ProductEvent(String productId, String type) {
        this.productId = productId;
        this.type = type;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ProductEvent{productId='" + productId + "', type='" + type + "'}";
    }
}

