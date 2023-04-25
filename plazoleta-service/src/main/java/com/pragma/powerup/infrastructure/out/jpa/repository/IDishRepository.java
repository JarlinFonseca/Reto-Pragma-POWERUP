package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.DishEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IDishRepository extends JpaRepository<DishEntity,Long> {

/*    @Query(nativeQuery = true, value = "select c.nombre as nombreCategoria, p.plato_id as id, p.nombre as nombrePlato, p.precio, p.descripcion as descripcionPlato, p.url_imagen as urlImagen, r.nombre as nombreRestaurante   from platos p \n" +
             "inner join categorias c on p.category_id  = c.categoria_id \n" +
             "inner join restaurantes r on p.restaurant_id = r.resturante_id \n" +
             "where p.restaurant_id  = :idRestaurante and p.activo = 1 \n" +
             "group by p.nombre , p.precio ,p.descripcion ,p.url_imagen , c.nombre, p.plato_id")
     List<Object[]> getDishesByRestaurant(@Param("idRestaurante") Integer idRestaurante);*/

    Page<DishEntity> findAllByRestauranteIdId(Long idRestaurante,  Pageable page);


   /* @Query(nativeQuery = true, value = "select  * from platos p")
    List<DishEntity> getDishesByRestaurant();*/
}
