package com.banreservas.inventory.messaging;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;


@ApplicationScoped
public class InventoryEventProcessor {

    private static final Logger LOGGER = Logger.getLogger(InventoryEventProcessor.class);

    @Incoming("product-events")
    @Outgoing("inventory-events")
    public InventoryEvent process(ProductEvent event) {
        if (event == null || event.getType() == null) {
            LOGGER.warn("Evento recibido es nulo o no tiene un tipo válido.");
            return null;
        }

        LOGGER.info("Procesando evento: " + event);

        switch (event.getType()) {
            case "CREATED":
                return handleProductCreated(event);
            case "DELETED":
                return handleProductDeleted(event);
            default:
                LOGGER.warn("Tipo de evento desconocido: " + event.getType());
                return null;
        }
    }

    private InventoryEvent handleProductCreated(ProductEvent event) {
        LOGGER.info("Creando inventario para producto: " + event.getProductId());
        return new InventoryEvent(event.getProductId(), "CREATED");
    }

    private InventoryEvent handleProductDeleted(ProductEvent event) {
        LOGGER.info("Eliminando inventario para producto: " + event.getProductId());
        return new InventoryEvent(event.getProductId(), "DELETED");
    }
}