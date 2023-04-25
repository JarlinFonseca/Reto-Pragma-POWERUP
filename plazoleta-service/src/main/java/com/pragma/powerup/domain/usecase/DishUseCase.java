package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.ICategoryServicePort;
import com.pragma.powerup.domain.api.IDishServicePort;
import com.pragma.powerup.domain.exception.CategoryNotExistException;
import com.pragma.powerup.domain.exception.DishNotExistException;
import com.pragma.powerup.domain.exception.OwnerNotAuthenticatedException;
import com.pragma.powerup.domain.exception.RestaurantNotExistException;
import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.spi.bearertoken.IToken;
import com.pragma.powerup.domain.spi.persistence.IDishPersistencePort;
import com.pragma.powerup.domain.spi.persistence.IRestaurantPersistencePort;
import com.pragma.powerup.domain.exception.OwnerAuthMustBeOwnerRestaurantException;


import java.util.ArrayList;
import java.util.List;


public class DishUseCase implements IDishServicePort {
    private final IDishPersistencePort dishPersistencePort;
     private  final IRestaurantPersistencePort restaurantPersistencePort;

    private  final IToken token;

    private  final ICategoryServicePort categoryServicePort;

    public DishUseCase(IDishPersistencePort dishPersistencePort, IRestaurantPersistencePort restaurantPersistencePort, IToken token, ICategoryServicePort categoryServicePort) {
        this.dishPersistencePort = dishPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.token = token;
        this.categoryServicePort = categoryServicePort;
    }

    @Override
    public void saveDish(DishModel dishModel) {
        if(restaurantPersistencePort.getRestaurantById(dishModel.getRestauranteId().getId())==null) throw new RestaurantNotExistException();
        if(categoryServicePort.getCategoryById(dishModel.getCategoriaId().getId())==null) throw new CategoryNotExistException();

        validateOwnerAuthWithOwnerRestaurant(dishModel);

        dishModel.setActivo(true);
        dishPersistencePort.saveDish(dishModel);
    }


    @Override
    public DishModel getDishById(Long id) {
        return dishPersistencePort.getDishById(id);
    }

    @Override
    public void updateDish(Long id, DishModel dishModel) {

        DishModel dishModel2 = dishPersistencePort.getDishById(id);
        if(dishModel2==null) throw new DishNotExistException();

        validateOwnerAuthWithOwnerRestaurant(dishModel2);

     //   dishModel2.setNombre(dishModel.getNombre());
        dishModel2.setPrecio(dishModel.getPrecio());
        dishModel2.setDescripcion(dishModel.getDescripcion());

        dishPersistencePort.saveDish(dishModel2);
    }

    @Override
    public void updateEnableDisableDish(Long idDish, Long flag) {
        DishModel dishModel2 = dishPersistencePort.getDishById(idDish);
        if(dishModel2==null) throw new DishNotExistException();

        validateOwnerAuthWithOwnerRestaurant(dishModel2);

        //Si el usuario le pasa el valor de 1 es enable, si le pasa otro valor es disable
        boolean isEnableOrDisable = (flag==1)?true:false;
        dishModel2.setActivo(isEnableOrDisable);

        dishPersistencePort.saveDish(dishModel2);
    }

    private void validateOwnerAuthWithOwnerRestaurant(DishModel dishModel){
        String bearerToken = token.getBearerToken();
        if(bearerToken==null) throw new OwnerNotAuthenticatedException();
        Long idOwnerAuth = token.getUsuarioAutenticadoId(bearerToken);
        Long idOwnerRestaurant =  restaurantPersistencePort.getRestaurantById(dishModel.getRestauranteId().getId()).getIdPropietario();
        if(idOwnerAuth!=idOwnerRestaurant) throw new OwnerAuthMustBeOwnerRestaurantException();
    }



    @Override
    public List<DishModel> getAllDishes() {
        return dishPersistencePort.getAllDishes();
    }

    @Override
    public List<DishModel> findAllByRestauranteId(Long idRestaurante, Integer page, Integer size) {
        List<DishModel> dishModelList=dishPersistencePort.findAllByRestauranteId(idRestaurante, page,size);
        List<DishModel> platosActivos=new ArrayList<>();
        for (DishModel dishModel:dishModelList) {
            if(dishModel.getActivo()){
              platosActivos.add(dishModel);
            }
        }
        return platosActivos;
    }

    @Override
    public void deleteDishById(Long id) {
        dishPersistencePort.deleteDishById(id);
    }
}
