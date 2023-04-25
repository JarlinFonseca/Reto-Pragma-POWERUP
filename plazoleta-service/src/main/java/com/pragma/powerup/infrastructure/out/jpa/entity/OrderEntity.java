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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pedidos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "pedido_id", nullable = false)
    private Long id;

    @Column(name = "cliente_id", nullable = false)
    private Long idCliente;
    @Column(name = "fecha", nullable = false)
    private Date fecha;
    @Column(name = "estado")
    private String estado;
    @ManyToOne
    @JoinColumn(name = "chef_id")
    private RestaurantEmployeeEntity chef;
    @ManyToOne
    @JoinColumn(name = "restaurante_id", nullable = false)
    private RestaurantEntity restaurante;


}
