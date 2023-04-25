package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.OrderDishModel;
import com.pragma.powerup.domain.model.OrderModel;
import com.pragma.powerup.domain.spi.persistence.IOrderPersistencePort;
import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup.infrastructure.out.jpa.entity.OrderDishEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.OrderEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IOrderDishEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IOrderEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IOrderDishRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OrderJpaAdapter implements IOrderPersistencePort {

    private final IOrderRepository orderRepository;

    private final IOrderEntityMapper orderEntityMapper;

    private final IOrderDishRepository orderDishRepository;

    private final IOrderDishEntityMapper orderDishEntityMapper;
    @Override
    public OrderModel saveOrder(OrderModel orderModel) {
        OrderEntity orderEntity= orderRepository.save(orderEntityMapper.toEntity(orderModel));
        return orderEntityMapper.toOrderModel(orderEntity);
    }

    @Override
    public void saveOrderDish(List<OrderDishModel> orderDishModels) {
        List<OrderDishEntity> orderDishEntities = new ArrayList<>();
        for (int i=0; i<orderDishModels.size();i++){
            orderDishEntities.add(orderDishEntityMapper.toEntity(orderDishModels.get(i)));
        }
       orderDishRepository.saveAll(orderDishEntities);
    }

    @Override
    public Boolean existsByIdClienteAndEstado(Long id, String estado) {
        return orderRepository.existsByIdClienteAndEstado(id, estado);
    }

    @Override
    public List<OrderModel> getAllOrdersWithPagination(Integer page, Integer size, Long restauranteId, String estado) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<OrderEntity>  orderEntityPage= orderRepository.findByRestaurante_idAndEstado(restauranteId,estado, pageable);
        if(orderEntityPage.isEmpty()){
            throw new NoDataFoundException();
        }
        return orderEntityPage
                .stream().map(orderEntityMapper::toOrderModel).collect(Collectors.toList());

    }

    @Override
    public List<OrderDishModel> getAllOrdersByPedido(Long pedido_id) {
        List<OrderDishEntity> orderDishEntities= orderDishRepository.findByPedido_Id(pedido_id);
        if(orderDishEntities.isEmpty()){
            throw new NoDataFoundException();
        }
        return orderDishEntityMapper.toOrderDishModelList(orderDishEntities);
    }

    @Override
    public OrderModel getOrderById(Long id) {
        Optional<OrderEntity> orderEntityOptional = orderRepository.findById(id);
        OrderEntity orderEntity= orderEntityOptional.orElse(null);
        return orderEntityMapper.toOrderModel(orderEntity);
    }

    @Override
    public Boolean existsByIdAndEstado(Long id, String estado) {
        return orderRepository.existsByIdAndEstado(id, estado);
    }

}
