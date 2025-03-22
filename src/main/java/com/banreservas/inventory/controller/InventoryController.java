package com.banreservas.inventory.controller;

import com.banreservas.inventory.entity.Inventory;
import com.banreservas.inventory.entity.InventoryMovement;
import com.banreservas.inventory.messaging.InventoryEvent;
import com.banreservas.inventory.repository.InventoryMovementRepository;
import com.banreservas.inventory.service.InventoryService;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.util.List;

import static org.eclipse.microprofile.reactive.messaging.Message.LOGGER;

@Path("/inventory")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
//@Authenticated
public class InventoryController {

    @Inject
    InventoryService inventoryService;

    @Inject
    InventoryMovementRepository inventoryMovementRepository;

    @GET
    @Path("/{productId}")
//    @RolesAllowed("user")
    public Inventory getInventory(@PathParam("productId") Long productId) {
        return inventoryService.getInventory(productId);
    }

    @POST
//    @RolesAllowed("admin")
    public Response adjustInventory(InventoryMovement adjustment) {
        Inventory inventory = inventoryService.adjustInventory(adjustment.getProductId(), adjustment.getQuantityChange());
        return Response.ok(inventory).build();
    }

    @GET
    @Path("/movements/{productId}")
//    @RolesAllowed("user")
    public List<InventoryMovement> getMovements(@PathParam("productId") Long productId) {
        return inventoryMovementRepository.findByProductId(productId);
    }

    // Método para recibir mensajes entrantes desde "inventory-events"
//    @Incoming("inventory-events")
//    public void receiveInventoryEvent(InventoryEvent event) {
//        LOGGER.info("Evento recibido: " + event);
//        inventoryService.handleInventoryEvent(event);
//    }

    @DELETE
    @Path("/{productId}")
//    @RolesAllowed("admin")
    public Response deleteInventory(@PathParam("productId") Long productId) {
        inventoryService.deleteProduct(productId);
        return Response.noContent().build();
    }
}
