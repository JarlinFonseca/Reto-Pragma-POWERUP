package com.pragma.powerup.infrastructure.out.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pedidos_platos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDishEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "pedido_plato_id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private OrderEntity pedido;
    @ManyToOne
    @JoinColumn(name = "plato_id", nullable = false)
    private DishEntity plato;
    @Column(name = "cantidad", nullable = false)
    private String cantidad;

}
