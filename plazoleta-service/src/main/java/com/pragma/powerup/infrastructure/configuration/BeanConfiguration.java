package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.ICategoryServicePort;
import com.pragma.powerup.domain.api.IDishServicePort;
import com.pragma.powerup.domain.api.IOrderServicePort;
import com.pragma.powerup.domain.api.IRestaurantEmployeeServicePort;
import com.pragma.powerup.domain.api.IRestaurantServicePort;
import com.pragma.powerup.domain.spi.bearertoken.IToken;
import com.pragma.powerup.domain.spi.feignclients.ITwilioFeignClientPort;
import com.pragma.powerup.domain.spi.persistence.ICategoryPersistencePort;
import com.pragma.powerup.domain.spi.persistence.IDishPersistencePort;
import com.pragma.powerup.domain.spi.persistence.IOrderPersistencePort;
import com.pragma.powerup.domain.spi.persistence.IRestaurantEmployeePersistencePort;
import com.pragma.powerup.domain.spi.persistence.IRestaurantPersistencePort;
import com.pragma.powerup.domain.spi.feignclients.IUserFeignClientPort;
import com.pragma.powerup.domain.usecase.CategoryUseCase;
import com.pragma.powerup.domain.usecase.DishUseCase;
import com.pragma.powerup.domain.usecase.OrderUseCase;
import com.pragma.powerup.domain.usecase.RestaurantEmployeeUseCase;
import com.pragma.powerup.domain.usecase.RestaurantUseCase;
import com.pragma.powerup.infrastructure.out.feignclients.TwilioFeignClients;
import com.pragma.powerup.infrastructure.out.feignclients.UserFeignClients;
import com.pragma.powerup.infrastructure.out.feignclients.adapter.TwilioFeignAdapter;
import com.pragma.powerup.infrastructure.out.feignclients.adapter.UserFeignAdapter;
import com.pragma.powerup.infrastructure.out.feignclients.mapper.ITwilioMapper;
import com.pragma.powerup.infrastructure.out.feignclients.mapper.IUserDtoMapper;
import com.pragma.powerup.infrastructure.out.jpa.adapter.CategoryJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.adapter.DishJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.adapter.OrderJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.adapter.RestaurantJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.adapter.RestaurantEmployeeJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.mapper.ICategoryEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IDishEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IOrderDishEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IOrderEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRestaurantEmployeeEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.ICategoryRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IDishRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IOrderDishRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IOrderRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRestaurantEmployeeRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRestaurantRepository;
import com.pragma.powerup.infrastructure.out.token.TokenAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IRestaurantRepository restaurantRepository;

    private final IRestaurantEntityMapper restaurantEntityMapper;

    private final IDishRepository dishRepository;

    private final IDishEntityMapper dishEntityMapper;


    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;


    private final UserFeignClients userFeignClient;
    private final IUserDtoMapper userDtoMapper;

    private  final IRestaurantEmployeeRepository restaurantEmployeeRepository;
    private final IRestaurantEmployeeEntityMapper restaurantEmployeeEntityMapper;

    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;

    private  final IOrderDishRepository orderDishRepository;
    private final IOrderDishEntityMapper orderDishEntityMapper;

    private final TwilioFeignClients twilioFeignClients;
    private final ITwilioMapper twilioMapper;

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort(){
        return new RestaurantJpaAdapter(restaurantRepository, restaurantEntityMapper);
    }

    @Bean
    public IUserFeignClientPort userFeignClientPort(){
        return new UserFeignAdapter(userFeignClient, userDtoMapper);
    }

    @Bean
    public IRestaurantServicePort restaurantServicePort(){

        return new RestaurantUseCase(restaurantPersistencePort(), userFeignClientPort());
    }

    @Bean
    public IToken token(){
        return new TokenAdapter();
    }

    @Bean
    public IDishPersistencePort dishPersistencePort(){

        return new DishJpaAdapter(dishRepository, dishEntityMapper);
    }
    @Bean
    public IDishServicePort dishServicePort(){

        return new DishUseCase(dishPersistencePort(), restaurantPersistencePort(), token(), categoryServicePort());
    }

    @Bean
    public ICategoryPersistencePort categoryPersistencePort(){
       return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort(){
        return new CategoryUseCase(categoryPersistencePort());
    }

    @Bean
    public IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort(){
        return new RestaurantEmployeeJpaAdapter(restaurantEmployeeRepository, restaurantEmployeeEntityMapper);
    }

    @Bean
    public IRestaurantEmployeeServicePort restaurantEmployeeServicePort(){
        return new RestaurantEmployeeUseCase(restaurantEmployeePersistencePort());
    }

    @Bean
    public IOrderPersistencePort orderPersistencePort(){
        return new OrderJpaAdapter(orderRepository, orderEntityMapper, orderDishRepository, orderDishEntityMapper);
    }

    @Bean
    public IOrderServicePort orderServicePort(){
        return new OrderUseCase(orderPersistencePort(), token(), restaurantPersistencePort(), dishPersistencePort(), restaurantEmployeePersistencePort(), twilioFeignClientPort(), userFeignClientPort());
    }

    @Bean
    ITwilioFeignClientPort twilioFeignClientPort(){
        return new TwilioFeignAdapter(twilioFeignClients, twilioMapper);
    }
}