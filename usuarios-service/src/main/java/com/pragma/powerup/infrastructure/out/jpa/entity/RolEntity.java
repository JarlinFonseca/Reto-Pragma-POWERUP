package com.pragma.powerup.infrastructure.out.jpa.entity;

import com.pragma.powerup.domain.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RolEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "rol_id",nullable = false)
    private Long id;
    //@NotNull(message = "El nombre del rol no puede ser nulo")

    @Column(name = "nombre")
    @NotBlank
    private  String nombre;
   // @NotNull(message = "La descripcion no puede ser nula")
    @Column(name = "descripcion")
    @NotBlank
    private String descripcion;

    //@OneToMany(mappedBy = "rol")
    //private List<UsuarioEntity> usuarios;


}
