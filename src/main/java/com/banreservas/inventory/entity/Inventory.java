//package com.banreservas.inventory.entity;
////import com.fasterxml.jackson.annotation.JsonProperty;
//
//import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
//import jakarta.persistence.*;
//import java.util.List;
//
//@Entity
//public class Inventory extends PanacheEntityBase {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    public Long id;
//
//    public Long productId;
//    public Integer quantity;
//
//    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL, orphanRemoval = true)
//    public List<InventoryMovement> movements;
//
//    // Constructors, getters, and setters can be added here
//}
//
