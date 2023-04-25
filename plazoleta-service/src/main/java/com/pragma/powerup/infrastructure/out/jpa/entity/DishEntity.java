package com.pragma.powerup.infrastructure.out.jpa.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "platos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DishEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "plato_id", nullable = false)
    private Long id;
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "precio", nullable = false)
    private String precio;
    @Column(name = "descripcion", nullable = false)
    private String descripcion;
    @Column(name = "url_imagen", nullable = false)
    private String urlImagen;
    @Column(name = "activo")
    private Boolean activo;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="restaurant_id")
    private RestaurantEntity restauranteId;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="category_id")
    private CategoryEntity categoriaId;
}
