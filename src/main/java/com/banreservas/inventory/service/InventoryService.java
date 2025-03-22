package com.banreservas.inventory.service;

import com.banreservas.inventory.entity.Inventory;
import com.banreservas.inventory.entity.InventoryMovement;
import com.banreservas.inventory.messaging.InventoryEvent;
import com.banreservas.inventory.messaging.InventoryEventProcessor;
import com.banreservas.inventory.messaging.ProductEvent;
import com.banreservas.inventory.repository.InventoryMovementRepository;
import com.banreservas.inventory.repository.InventoryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

@ApplicationScoped
public class InventoryService {

    private static final Logger LOGGER = Logger.getLogger(InventoryService.class);

    @Inject
    InventoryRepository inventoryRepository;

    @Inject
    InventoryMovementRepository inventoryMovementRepository;

    @Inject
    @Channel("product-events")
    Emitter<String> emitter;

    @Inject
    InventoryEventProcessor eventProcessor;

    /**
     * Recibe mensajes de Kafka desde el canal "product-events".
     */
    @Incoming("product-events")
    public void receiveMessage(ProductEvent event) {
        if (event == null) {
            LOGGER.warn("Evento recibido es nulo.");
            return;
        }

        LOGGER.info("Mensaje recibido de Kafka: " + event);

        // Procesa el evento y emite un nuevo evento de inventario si es necesario
        InventoryEvent inventoryEvent = eventProcessor.process(event);
        if (inventoryEvent != null) {
            sendMessage(inventoryEvent);
        }
    }

    /**
     * Envía mensajes a Kafka en el canal "inventory-events".
     */
    public void sendMessage(InventoryEvent event) {
        String message = event.toString(); // Convertir a JSON si es necesario
        LOGGER.info("Enviando mensaje a Kafka: " + message);
        emitter.send(message)
                .whenComplete((result, throwable) -> {
                    if (throwable == null) {
                        LOGGER.info("Mensaje enviado correctamente a Kafka.");
                    } else {
                        LOGGER.error("Error al enviar mensaje a Kafka: ", throwable);
                    }
                });
    }

    /**
     * Método para eliminar un producto de la base de datos.
     */
    @Transactional
    public void deleteProduct(Long productId) {
        Inventory inventory = inventoryRepository.findById(productId);
        if (inventory != null) {
            inventoryRepository.delete(inventory);
            LOGGER.info("Producto eliminado: " + productId);
        } else {
            LOGGER.warn("Intento de eliminar un producto que no existe: " + productId);
        }
    }


    @Transactional
    public Inventory adjustInventory(long productId, int quantityChange) {
        inventoryRepository.adjustQuantity(productId, quantityChange);

        InventoryMovement movement = new InventoryMovement();
        movement.productId = productId;
        movement.quantityChange = quantityChange;
        movement.movementType = quantityChange > 0 ? "IN" : "OUT";
        movement.movementDate = LocalDateTime.now();
        inventoryMovementRepository.persist(movement);

        return inventoryRepository.findByProductId(productId);
    }

    public Inventory getInventory(Long productId) {
        return inventoryRepository.findByProductId(productId);
    }
}

