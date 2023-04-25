package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.spi.persistence.IRestaurantPersistencePort;
import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class RestaurantJpaAdapter implements IRestaurantPersistencePort {

    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;
    @Override
    public RestaurantModel saveRestaurant(RestaurantModel restaurantModel) {
        RestaurantEntity restaurantEntity = restaurantRepository.save(restaurantEntityMapper.toEntity(restaurantModel));
        return restaurantEntityMapper.toRestaurantModel(restaurantEntity);
    }

    @Override
    public RestaurantModel getRestaurantById(Long id) {
        Optional<RestaurantEntity>  optionalRestaurantEntity= restaurantRepository.findById(id);
        RestaurantEntity restaurantEntity = optionalRestaurantEntity.orElse(null);
        return restaurantEntityMapper.toRestaurantModel(restaurantEntity);
    }

    @Override
    public RestaurantModel getRestaurantByIdPropietario(Long id_propietario) {
        Optional<RestaurantEntity>  optionalRestaurantEntity= restaurantRepository.findByIdPropietario(id_propietario);
        RestaurantEntity restaurantEntity = optionalRestaurantEntity.orElse(null);
        return restaurantEntityMapper.toRestaurantModel(restaurantEntity);
    }

    @Override
    public List<RestaurantModel> getAllRestaurants() {
        List<RestaurantEntity> restaurantEntityList = (List<RestaurantEntity>) restaurantRepository.findAll();
        if(restaurantEntityList.isEmpty()){
            throw  new NoDataFoundException();
        }
        return restaurantEntityMapper.toRestaurantModelList(restaurantEntityList);
    }

    @Override
    public List<RestaurantModel> getRestaurantsWithPagination(Integer page, Integer size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "nombre"));
        Page<RestaurantEntity> restaurantPage = restaurantRepository.findAll(pageable);
        //List<RestaurantEntity> restaurantEntityList = (List<RestaurantEntity>) restaurantRepository.findAll(pageable);
        List<RestaurantEntity> restaurantEntityList = restaurantPage.getContent();
        if(restaurantEntityList.isEmpty()){
            throw new NoDataFoundException();
        }
        return restaurantEntityMapper.toRestaurantModelList(restaurantEntityList);
    }

    @Override
    public void deleteRestaurantById(Long id) {
        restaurantRepository.deleteById(id);
    }
}
