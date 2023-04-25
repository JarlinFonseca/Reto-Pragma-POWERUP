package com.pragma.powerup.infrastructure.out.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "restaurantes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RestaurantEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "resturante_id", nullable = false)
    private Long id;
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "nit", unique = true, nullable = false)
    private String nit;
    @Column(name = "direccion", nullable = false)
    private String direccion;
    @Column(name = "telefono", nullable = false)
    private String telefono;
    @Column(name = "urlLogo", nullable = false)
    private String urlLogo;
    @Column(name = "id_propietario", nullable = false)
    private Long idPropietario;
  /*@OneToMany(fetch = FetchType.EAGER,mappedBy = "restauranteId")
    private List<DishEntity> platos;*/
}
