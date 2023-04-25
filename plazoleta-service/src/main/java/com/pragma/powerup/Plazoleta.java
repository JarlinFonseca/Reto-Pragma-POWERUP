package com.pragma.powerup;

import com.pragma.powerup.domain.model.OrderDishModel;
import com.pragma.powerup.infrastructure.out.jpa.entity.CategoryEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.DishEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.OrderDishEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.OrderEntity;
import com.pragma.powerup.infrastructure.out.jpa.repository.ICategoryRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IDishRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IOrderDishRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;


@EnableFeignClients
@SpringBootApplication
public class Plazoleta implements CommandLineRunner {

	@Autowired
	private IDishRepository dishRepository;

	@Autowired
	private IOrderDishRepository orderDishRepository;

	@Autowired
	private IOrderRepository orderRepository;

	@Autowired
	private ICategoryRepository categoryRepository;
	public static void main(String[] args) {
		SpringApplication.run(Plazoleta.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<DishEntity> dishEntityList= dishRepository.findAll();
		for (DishEntity valor:dishEntityList) {
			System.out.println(valor.getCategoriaId().getId());
		}
		Optional<DishEntity> dishEntity = dishRepository.findById(2L);
		DishEntity dishEntity1= dishEntity.orElse(null);
		System.out.println(dishEntity1.getCategoriaId());

		Optional<CategoryEntity> categoryEntity = categoryRepository.findById(1L);
		CategoryEntity categoryEntity1 = categoryEntity.orElse(null);


		// Obteniendo ID de la categoria
		// System.out.println(categoryEntity1.getId());
		// Obteniendo los platos de la categoria
		//System.out.println(categoryEntity1.getPlatos());

/*
		List<Object[]> dishPrueba = dishRepository.getDishesByRestaurant(3);
		for (Object[] valor:dishPrueba) {
			System.out.println(valor[0]);
			System.out.println(valor[1]);
			System.out.println(valor[2]);
			System.out.println(valor[3]);
			System.out.println(valor[4]);
			System.out.println(valor[5]);
			System.out.println(valor[6]);
*/


			//System.out.println(valor.getPrecio());
		//}

		Optional<OrderEntity> orderEntity = orderRepository.findById(4L);
		OrderEntity orderEntity1= orderEntity.orElse(null);


	//	PageRequest pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.ASC, "estado"));

	//	List<OrderEntity> orderEntities = orderRepository.findByRestaurante_IdAndEstado(3L,"PENDIENTE", pageable);

	//	System.out.println(orderEntities);

		/*List<OrderDishEntity>  pedidoPlatos = orderDishRepository.findByPedido_Id(4L);
		for(OrderDishEntity plato:pedidoPlatos){
			System.out.println(plato.getPedido().getEstado());
		}*/

		//System.out.println(orderEntity1);




	}
}
